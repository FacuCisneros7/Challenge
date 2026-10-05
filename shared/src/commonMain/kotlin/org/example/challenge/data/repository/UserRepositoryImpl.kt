package org.example.challenge.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import org.example.challenge.domain.model.AppResult
import org.example.challenge.domain.model.UserProfile
import org.example.challenge.domain.repository.UserRepository

class UserRepositoryImpl : UserRepository {
    private val firestore = Firebase.firestore

    override suspend fun getProfile(userId: String): AppResult<UserProfile?> {
        return try {
            val doc = firestore.collection("users").document(userId).get()
            if (!doc.exists) {
                return AppResult.Success(null)
            }
            val createdTs = doc.get<Timestamp?>("createdAt")
            val createdInstant = createdTs?.let { Instant.fromEpochSeconds(it.seconds, it.nanoseconds) } ?: Instant.DISTANT_PAST

            val favSnapshot = firestore.collection("users").document(userId).collection("favorites").get()
            val favorites = favSnapshot.documents.map { it.id }

            val profile = UserProfile(
                userId = userId,
                username = doc.get<String>("username"),
                bio = doc.get<String>("bio"),
                createdAt = createdInstant,
                favorites = favorites
            )
            AppResult.Success(profile)
        } catch (e: Exception) {
            AppResult.Error(e.message ?: "Error al obtener el perfil de usuario", e)
        }
    }

    override suspend fun updateProfile(userId: String, username: String, bio: String): AppResult<Unit> {
        return try {
            val ref = firestore.collection("users").document(userId)
            val doc = ref.get()
            val now = Timestamp.now()
            val data = mutableMapOf<String, Any>(
                "username" to username,
                "bio" to bio
            )
            if (!doc.exists) {
                data["createdAt"] = now
                ref.set(data)
            } else {
                ref.update(data)
            }
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Error(e.message ?: "Error al actualizar el perfil", e)
        }
    }

    override fun observeFavorites(userId: String): Flow<List<String>> {
        return firestore.collection("users").document(userId).collection("favorites")
            .snapshots()
            .map { snapshot ->
                snapshot.documents.map { it.id }
            }
    }

    override suspend fun toggleFavorite(userId: String, matchId: String): AppResult<Unit> {
        return try {
            val favRef = firestore.collection("users").document(userId).collection("favorites").document(matchId)
            val doc = favRef.get()
            val now = Timestamp.now()
            if (doc.exists) {
                favRef.delete()
            } else {
                val data = mapOf("matchId" to matchId, "savedAt" to now)
                favRef.set(data)
            }
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Error(e.message ?: "Error al cambiar favorito", e)
        }
    }
}
