package org.example.challenge.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    val followingUserIds: List<String> = emptyList(),
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

    private var observeFollowingJob: Job? = null

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

                // Observe Following list
                observeFollowingJob?.cancel()
                observeFollowingJob = viewModelScope.launch {
                    userRepository.observeFollowing(userId).collect { followingList ->
                        val ids = followingList.map { it.userId }
                        _uiState.update { it.copy(followingUserIds = ids) }
                    }
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

    fun toggleFollowUser(targetUserId: String, targetUsername: String) {
        viewModelScope.launch {
            val currentUserId = _uiState.value.currentUserId
            val currentUsername = _uiState.value.currentUsername.ifBlank { "Usuario" }
            if (currentUserId.isBlank() || currentUserId == targetUserId) return@launch

            userRepository.toggleFollow(
                currentUserId,
                currentUsername,
                targetUserId,
                targetUsername
            )
        }
    }

    fun saveReview(rating: Int, text: String) {
        viewModelScope.launch {
            var userId = _uiState.value.currentUserId
            if (userId.isBlank()) {
                userId = authRepository.currentUserId.firstOrNull() ?: ""
            }
            if (userId.isBlank()) {
                _uiState.update { it.copy(errorMessage = "Debes iniciar sesión para publicar una reseña") }
                return@launch
            }

            _uiState.update { it.copy(isSubmitting = true, errorMessage = null) }

            val username = _uiState.value.currentUsername.ifBlank { "Usuario" }
            val existingReview = _uiState.value.userReview

            val reviewToSave = Review(
                id = existingReview?.id ?: "${userId}_$matchId",
                matchId = matchId,
                userId = userId,
                username = username,
                rating = rating,
                text = text.trim(),
                createdAt = existingReview?.createdAt ?: Instant.DISTANT_PAST,
                updatedAt = Instant.DISTANT_PAST
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
