package org.example.challenge.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.example.challenge.domain.model.AppResult
import org.example.challenge.domain.model.Match
import org.example.challenge.domain.model.Review
import org.example.challenge.domain.repository.AuthRepository
import org.example.challenge.domain.repository.MatchRepository
import org.example.challenge.domain.repository.ReviewRepository
import org.example.challenge.domain.repository.UserRepository

data class DetailUiState(
    val isLoading: Boolean = true,
    val match: Match? = null,
    val isFavorite: Boolean = false,
    val reviews: List<Review> = emptyList(),
    val averageRating: Double = 0.0,
    val userReview: Review? = null,
    val currentUserId: String = "",
    val currentUsername: String = "",
    val isSubmitting: Boolean = false,
    val errorMessage: String? = null
)

class DetailViewModel(
    val matchId: String,
    private val authRepository: AuthRepository,
    private val matchRepository: MatchRepository,
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadMatchDetail()
    }

    fun loadMatchDetail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val userId = authRepository.currentUserId.firstOrNull() ?: ""
            _uiState.update { it.copy(currentUserId = userId) }

            if (userId.isNotBlank()) {
                val profileResult = userRepository.getProfile(userId)
                if (profileResult is AppResult.Success) {
                    val username = profileResult.data?.username ?: "Usuario"
                    _uiState.update { it.copy(currentUsername = username) }
                }
            }

            when (val matchResult = matchRepository.getMatch(matchId)) {
                is AppResult.Success -> {
                    _uiState.update { it.copy(match = matchResult.data) }
                }
                is AppResult.Error -> {
                    _uiState.update { it.copy(errorMessage = matchResult.message) }
                }
            }

            loadReviews()

            if (userId.isNotBlank()) {
                userRepository.observeFavorites(userId).collect { favorites ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isFavorite = favorites.contains(matchId)
                        )
                    }
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private suspend fun loadReviews() {
        when (val reviewsResult = reviewRepository.getReviewsForMatch(matchId)) {
            is AppResult.Success -> {
                val reviews = reviewsResult.data
                val userId = _uiState.value.currentUserId
                val userRev = reviews.find { it.userId == userId }
                val avg = if (reviews.isNotEmpty()) reviews.map { it.rating }.average() else 0.0

                _uiState.update {
                    it.copy(
                        reviews = reviews,
                        userReview = userRev,
                        averageRating = avg
                    )
                }
            }
            is AppResult.Error -> {}
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val userId = _uiState.value.currentUserId
            if (userId.isBlank()) return@launch

            userRepository.toggleFavorite(userId, matchId)
        }
    }

    fun saveReview(rating: Int, text: String) {
        viewModelScope.launch {
            val userId = _uiState.value.currentUserId
            val username = _uiState.value.currentUsername.ifBlank { "Usuario" }
            if (userId.isBlank()) return@launch

            _uiState.update { it.copy(isSubmitting = true) }

            val existingReview = _uiState.value.userReview
            val now = Instant.DISTANT_FUTURE // placeholder or epoch millis
            val reviewToSave = Review(
                id = existingReview?.id ?: "${userId}_$matchId",
                matchId = matchId,
                userId = userId,
                username = username,
                rating = rating,
                text = text.trim(),
                createdAt = existingReview?.createdAt ?: now,
                updatedAt = now
            )

            when (val result = reviewRepository.saveReview(reviewToSave)) {
                is AppResult.Success -> {
                    _uiState.update { it.copy(isSubmitting = false) }
                    loadReviews()
                }
                is AppResult.Error -> {
                    _uiState.update { it.copy(isSubmitting = false, errorMessage = result.message) }
                }
            }
        }
    }

    fun deleteReview(reviewId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true) }
            when (reviewRepository.deleteReview(reviewId)) {
                is AppResult.Success -> {
                    _uiState.update { it.copy(isSubmitting = false) }
                    loadReviews()
                }
                is AppResult.Error -> {
                    _uiState.update { it.copy(isSubmitting = false) }
                }
            }
        }
    }
}
