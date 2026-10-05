package org.example.challenge.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.challenge.domain.model.AppResult

/**
 * Repository interface for Authentication operations.
 */
interface AuthRepository {
    val currentUserId: Flow<String?>

    suspend fun signUp(email: String, password: String, username: String): AppResult<String>

    suspend fun signIn(email: String, password: String): AppResult<String>

    suspend fun signOut(): AppResult<Unit>
}
