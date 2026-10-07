package org.example.challenge.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.challenge.domain.model.AppResult
import org.example.challenge.domain.repository.AuthRepository
import org.example.challenge.domain.repository.UserRepository

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val currentUserId: StateFlow<String?> = authRepository.currentUserId
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun login(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            when (val result = authRepository.signIn(email, password)) {
                is AppResult.Success -> onSuccess(result.data)
                is AppResult.Error -> onError(result.message)
            }
        }
    }

    fun register(
        email: String,
        password: String,
        username: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            when (val result = authRepository.signUp(email, password, username)) {
                is AppResult.Success -> {
                    val uid = result.data
                    userRepository.updateProfile(uid, username, "Amante del fútbol ⚽")
                    onSuccess(uid)
                }

                is AppResult.Error -> onError(result.message)
            }
        }
    }

    fun signOut(onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            authRepository.signOut()
            onComplete()
        }
    }
}
