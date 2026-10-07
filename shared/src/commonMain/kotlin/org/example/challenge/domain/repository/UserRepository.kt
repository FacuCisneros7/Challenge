package org.example.challenge.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.example.challenge.domain.model.AppResult
import org.example.challenge.domain.model.UserProfile

/**
 * Repository interface for user profile, favorites, and follows management.
 */
interface UserRepository {
    val isDarkMode: StateFlow<Boolean>

    suspend fun getProfile(userId: String): AppResult<UserProfile?>

    suspend fun updateProfile(userId: String, username: String, bio: String): AppResult<Unit>

    suspend fun updateThemePreference(userId: String, isDarkMode: Boolean): AppResult<Unit>

    fun observeFavorites(userId: String): Flow<List<String>>

    suspend fun toggleFavorite(userId: String, matchId: String): AppResult<Unit>

    fun observeFollowers(userId: String): Flow<List<UserProfile>>

    fun observeFollowing(userId: String): Flow<List<UserProfile>>

    suspend fun toggleFollow(currentUserId: String, currentUsername: String, targetUserId: String, targetUsername: String): AppResult<Unit>
}
