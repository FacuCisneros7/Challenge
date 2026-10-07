package org.example.challenge.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.challenge.domain.model.AppResult
import org.example.challenge.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    private val firebaseAuth = Firebase.auth

    override val currentUserId: Flow<String?> = flow {
        emit(firebaseAuth.currentUser?.uid)
    }

    override suspend fun signUp(
        email: String,
        password: String,
        username: String
    ): AppResult<String> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password)
            val user = authResult.user
            if (user != null) {
                AppResult.Success(user.uid)
            } else {
                AppResult.Error("Error al registrar usuario: usuario nulo")
            }
        } catch (e: Exception) {
            AppResult.Error(e.message ?: "Error desconocido al registrar usuario", e)
        }
    }

    override suspend fun signIn(email: String, password: String): AppResult<String> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password)
            val user = authResult.user
            if (user != null) {
                AppResult.Success(user.uid)
            } else {
                AppResult.Error("Error al iniciar sesión: usuario nulo")
            }
        } catch (e: Exception) {
            val message = when {
                e.message?.contains(
                    "no user record",
                    ignoreCase = true
                ) == true -> "El usuario no existe."

                e.message?.contains(
                    "password",
                    ignoreCase = true
                ) == true || e.message?.contains(
                    "credential",
                    ignoreCase = true
                ) == true -> "Email o contraseña incorrectos."

                else -> e.message ?: "Error al iniciar sesión"
            }
            AppResult.Error(message, e)
        }
    }

    override suspend fun signOut(): AppResult<Unit> {
        return try {
            firebaseAuth.signOut()
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Error(e.message ?: "Error al cerrar sesión", e)
        }
    }
}
