package org.example.challenge.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.challenge.domain.model.AppResult
import org.example.challenge.domain.model.Match
import org.example.challenge.domain.model.Review
import org.example.challenge.domain.repository.AuthRepository
import org.example.challenge.domain.repository.MatchRepository
import org.example.challenge.domain.repository.ReviewRepository
import org.example.challenge.domain.repository.UserRepository

data class ProfileUiState(
    val isLoading: Boolean = true,
    val userId: String = "",
    val username: String = "",
    val bio: String = "",
    val userReviews: List<Review> = emptyList(),
    val favoriteMatches: List<Match> = emptyList(),
    val errorMessage: String? = null,
    val isEditing: Boolean = false,
    val isUpdating: Boolean = false
)

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository,
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfileData()
    }

    fun loadProfileData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val userId = authRepository.currentUserId.firstOrNull() ?: ""
            if (userId.isBlank()) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "No hay usuario autenticado") }
                return@launch
            }

            _uiState.update { it.copy(userId = userId) }

            when (val profileResult = userRepository.getProfile(userId)) {
                is AppResult.Success -> {
                    val profile = profileResult.data
                    if (profile != null) {
                        _uiState.update {
                            it.copy(
                                username = profile.username.ifBlank { "Usuario" },
                                bio = profile.bio.ifBlank { "Sin biografía" }
                            )
                        }
                    } else {
                        val defaultName = "Usuario_${userId.take(5)}"
                        val defaultBio = "Amante del fútbol ⚽"
                        userRepository.updateProfile(userId, defaultName, defaultBio)
                        _uiState.update {
                            it.copy(
                                username = defaultName,
                                bio = defaultBio
                            )
                        }
                    }
                }
                is AppResult.Error -> {
                    _uiState.update { it.copy(errorMessage = profileResult.message) }
                }
            }

            when (val reviewsResult = reviewRepository.getReviewsByUser(userId)) {
                is AppResult.Success -> {
                    _uiState.update { it.copy(userReviews = reviewsResult.data) }
                }
                is AppResult.Error -> {}
            }

            userRepository.observeFavorites(userId).collect { favoriteIds ->
                val matches = mutableListOf<Match>()
                for (id in favoriteIds) {
                    val matchResult = matchRepository.getMatch(id)
                    if (matchResult is AppResult.Success && matchResult.data != null) {
                        matches.add(matchResult.data)
                    }
                }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        favoriteMatches = matches
                    )
                }
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

            when (val result = userRepository.updateProfile(userId, newUsername.trim(), newBio.trim())) {
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
