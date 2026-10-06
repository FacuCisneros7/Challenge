package org.example.challenge.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.challenge.domain.model.AppResult
import org.example.challenge.domain.model.Match
import org.example.challenge.domain.model.Review
import org.example.challenge.domain.repository.AuthRepository
import org.example.challenge.domain.repository.MatchRepository
import org.example.challenge.domain.repository.ReviewRepository
import org.example.challenge.domain.repository.UserRepository

data class UserProfileDetailUiState(
    val isLoading: Boolean = true,
    val userId: String = "",
    val username: String = "",
    val bio: String = "",
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val isFollowing: Boolean = false,
    val currentUserId: String = "",
    val currentUsername: String = "",
    val userReviews: List<Review> = emptyList(),
    val favoriteMatches: List<Match> = emptyList(),
    val errorMessage: String? = null
)

class UserProfileDetailViewModel(
    val targetUserId: String,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository,
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserProfileDetailUiState())
    val uiState: StateFlow<UserProfileDetailUiState> = _uiState.asStateFlow()

    private var observeFollowingJob: Job? = null
    private var observeFavoritesJob: Job? = null

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val currentUserId = authRepository.currentUserId.firstOrNull() ?: ""
            _uiState.update { it.copy(currentUserId = currentUserId) }

            if (currentUserId.isNotBlank()) {
                val currentProfile = userRepository.getProfile(currentUserId)
                if (currentProfile is AppResult.Success) {
                    _uiState.update { it.copy(currentUsername = currentProfile.data?.username ?: "Usuario") }
                }
            }

            // Load target user profile
            when (val profileResult = userRepository.getProfile(targetUserId)) {
                is AppResult.Success -> {
                    val profile = profileResult.data
                    if (profile != null) {
                        _uiState.update {
                            it.copy(
                                userId = profile.userId,
                                username = profile.username.ifBlank { "Usuario" },
                                bio = profile.bio.ifBlank { "Sin biografía" },
                                followersCount = profile.followersCount,
                                followingCount = profile.followingCount
                            )
                        }
                    }
                }
                is AppResult.Error -> {
                    _uiState.update { it.copy(errorMessage = profileResult.message) }
                }
            }

            // Load target user reviews
            when (val reviewsResult = reviewRepository.getReviewsByUser(targetUserId)) {
                is AppResult.Success -> {
                    _uiState.update { it.copy(userReviews = reviewsResult.data) }
                }
                is AppResult.Error -> {}
            }

            // Done with primary load
            _uiState.update { it.copy(isLoading = false) }

            // Observe if current user follows target user in separate coroutine
            if (currentUserId.isNotBlank()) {
                observeFollowingJob?.cancel()
                observeFollowingJob = viewModelScope.launch {
                    userRepository.observeFollowing(currentUserId).collect { followingList ->
                        val isFol = followingList.any { it.userId == targetUserId }
                        _uiState.update { it.copy(isFollowing = isFol) }
                    }
                }
            }

            // Observe target user favorites in separate coroutine
            observeFavoritesJob?.cancel()
            observeFavoritesJob = viewModelScope.launch {
                userRepository.observeFavorites(targetUserId).collect { favoriteIds ->
                    val matches = mutableListOf<Match>()
                    for (id in favoriteIds) {
                        val matchResult = matchRepository.getMatch(id)
                        if (matchResult is AppResult.Success && matchResult.data != null) {
                            matches.add(matchResult.data)
                        }
                    }
                    _uiState.update {
                        it.copy(favoriteMatches = matches)
                    }
                }
            }
        }
    }

    fun toggleFollow() {
        viewModelScope.launch {
            val currentUserId = _uiState.value.currentUserId
            val currentUsername = _uiState.value.currentUsername
            val username = _uiState.value.username
            if (currentUserId.isBlank() || currentUserId == targetUserId) return@launch

            userRepository.toggleFollow(currentUserId, currentUsername, targetUserId, username)
        }
    }
}
