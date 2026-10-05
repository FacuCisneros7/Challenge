package org.example.challenge.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.challenge.domain.model.AppResult
import org.example.challenge.domain.model.UserProfile

/**
 * Repository interface for user profile and favorites management.
 */
interface UserRepository {
    suspend fun getProfile(userId: String): AppResult<UserProfile?>

    suspend fun updateProfile(userId: String, username: String, bio: String): AppResult<Unit>

    fun observeFavorites(userId: String): Flow<List<String>>

    suspend fun toggleFavorite(userId: String, matchId: String): AppResult<Unit>
}
