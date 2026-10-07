package org.example.challenge.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.challenge.domain.model.AppResult
import org.example.challenge.domain.model.Match
import org.example.challenge.domain.model.Review
import org.example.challenge.domain.model.UserProfile
import org.example.challenge.domain.repository.AuthRepository
import org.example.challenge.domain.repository.MatchRepository
import org.example.challenge.domain.repository.ReviewRepository
import org.example.challenge.domain.repository.UserRepository

data class ProfileUiState(
    val isLoading: Boolean = true,
    val userId: String = "",
    val username: String = "",
    val bio: String = "",
    val isDarkMode: Boolean = true,
    val followers: List<UserProfile> = emptyList(),
    val following: List<UserProfile> = emptyList(),
    val userReviews: List<Review> = emptyList(),
    val favoriteMatches: List<Match> = emptyList(),
    val errorMessage: String? = null,
    val isEditing: Boolean = false,
    val isUpdating: Boolean = false
) {
    val followersCount: Int get() = followers.size
    val followingCount: Int get() = following.size
}

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository,
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private var observeFavoritesJob: Job? = null
    private var observeFollowersJob: Job? = null
    private var observeFollowingJob: Job? = null

    init {
        loadProfileData()
    }

    fun loadProfileData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            var userId = authRepository.currentUserId.firstOrNull() ?: ""
            if (userId.isBlank()) {
                userId = authRepository.currentUserId.firstOrNull() ?: ""
            }
            if (userId.isBlank()) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "No hay usuario autenticado"
                    )
                }
                return@launch
            }

            _uiState.update { it.copy(userId = userId) }

            // Load User Profile
            when (val profileResult = userRepository.getProfile(userId)) {
                is AppResult.Success -> {
                    val profile = profileResult.data
                    if (profile != null) {
                        _uiState.update {
                            it.copy(
                                username = profile.username.ifBlank { "Usuario" },
                                bio = profile.bio.ifBlank { "Sin biografía" },
                                isDarkMode = profile.isDarkMode,
                                followers = profile.followers,
                                following = profile.following
                            )
                        }
                    } else {
                        val defaultName = "Usuario_${userId.take(5)}"
                        val defaultBio = "Amante del fútbol ⚽"
                        userRepository.updateProfile(userId, defaultName, defaultBio)
                        _uiState.update {
                            it.copy(
                                username = defaultName,
                                bio = defaultBio,
                                isDarkMode = true,
                                followers = emptyList(),
                                following = emptyList()
                            )
                        }
                    }
                }

                is AppResult.Error -> {
                    _uiState.update { it.copy(errorMessage = profileResult.message) }
                }
            }

            // Load User Reviews
            when (val reviewsResult = reviewRepository.getReviewsByUser(userId)) {
                is AppResult.Success -> {
                    _uiState.update { it.copy(userReviews = reviewsResult.data) }
                }

                is AppResult.Error -> {}
            }

            _uiState.update { it.copy(isLoading = false) }

            // Observe Followers asynchronously
            observeFollowersJob?.cancel()
            observeFollowersJob = viewModelScope.launch {
                userRepository.observeFollowers(userId).collect { followers ->
                    _uiState.update { it.copy(followers = followers) }
                }
            }

            // Observe Following asynchronously
            observeFollowingJob?.cancel()
            observeFollowingJob = viewModelScope.launch {
                userRepository.observeFollowing(userId).collect { following ->
                    _uiState.update { it.copy(following = following) }
                }
            }

            // Observe Favorites asynchronously
            observeFavoritesJob?.cancel()
            observeFavoritesJob = viewModelScope.launch {
                userRepository.observeFavorites(userId).collect { favoriteIds ->
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

    fun toggleDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            val userId = _uiState.value.userId
            if (userId.isNotBlank()) {
                _uiState.update { it.copy(isDarkMode = isDarkMode) }
                userRepository.updateThemePreference(userId, isDarkMode)
            }
        }
    }

    fun openEditDialog() {
        _uiState.update { it.copy(isEditing = true) }
    }

    fun closeEditDialog() {
        _uiState.update { it.copy(isEditing = false) }
    }

    fun updateProfile(newUsername: String, newBio: String) {
        viewModelScope.launch {
            val userId = _uiState.value.userId
            if (userId.isBlank()) return@launch

            _uiState.update { it.copy(isUpdating = true) }

            when (val result =
                userRepository.updateProfile(userId, newUsername.trim(), newBio.trim())) {
                is AppResult.Success -> {
                    _uiState.update {
                        it.copy(
                            username = newUsername.trim(),
                            bio = newBio.trim(),
                            isEditing = false,
                            isUpdating = false
                        )
                    }
                }

                is AppResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isUpdating = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    fun signOut(onComplete: () -> Unit) {
        viewModelScope.launch {
            authRepository.signOut()
            onComplete()
        }
    }
}
