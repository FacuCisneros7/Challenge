package org.example.challenge.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
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
            val createdTs = doc.get("createdAt", Timestamp.serializer().nullable)
            val createdInstant = createdTs?.let { Instant.fromEpochSeconds(it.seconds, it.nanoseconds) } ?: Instant.DISTANT_PAST

            val favSnapshot = firestore.collection("users").document(userId).collection("favorites").get()
            val favorites = favSnapshot.documents.map { it.id }

            val followersSnapshot = firestore.collection("users").document(userId).collection("followers").get()
            val followers = followersSnapshot.documents.map { docItem ->
                UserProfile(
                    userId = docItem.id,
                    username = docItem.get("username", String.serializer().nullable) ?: "Usuario",
                    bio = docItem.get("bio", String.serializer().nullable) ?: "",
                    createdAt = Instant.DISTANT_PAST
                )
            }

            val followingSnapshot = firestore.collection("users").document(userId).collection("following").get()
            val following = followingSnapshot.documents.map { docItem ->
                UserProfile(
                    userId = docItem.id,
                    username = docItem.get("username", String.serializer().nullable) ?: "Usuario",
                    bio = docItem.get("bio", String.serializer().nullable) ?: "",
                    createdAt = Instant.DISTANT_PAST
                )
            }

            val profile = UserProfile(
                userId = userId,
                username = doc.get("username", String.serializer().nullable) ?: "",
                bio = doc.get("bio", String.serializer().nullable) ?: "",
                createdAt = createdInstant,
                favorites = favorites,
                followers = followers,
                following = following
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

    override fun observeFollowers(userId: String): Flow<List<UserProfile>> {
        return firestore.collection("users").document(userId).collection("followers")
            .snapshots()
            .map { snapshot ->
                snapshot.documents.map { doc ->
                    UserProfile(
                        userId = doc.id,
                        username = doc.get("username", String.serializer().nullable) ?: "Usuario",
                        bio = doc.get("bio", String.serializer().nullable) ?: "",
                        createdAt = Instant.DISTANT_PAST
                    )
                }
            }
    }

    override fun observeFollowing(userId: String): Flow<List<UserProfile>> {
        return firestore.collection("users").document(userId).collection("following")
            .snapshots()
            .map { snapshot ->
                snapshot.documents.map { doc ->
                    UserProfile(
                        userId = doc.id,
                        username = doc.get("username", String.serializer().nullable) ?: "Usuario",
                        bio = doc.get("bio", String.serializer().nullable) ?: "",
                        createdAt = Instant.DISTANT_PAST
                    )
                }
            }
    }

    override suspend fun toggleFollow(currentUserId: String, currentUsername: String, targetUserId: String, targetUsername: String): AppResult<Unit> {
        return try {
            val followerRef = firestore.collection("users").document(targetUserId).collection("followers").document(currentUserId)
            val followingRef = firestore.collection("users").document(currentUserId).collection("following").document(targetUserId)

            val isFollowing = followerRef.get().exists
            val now = Timestamp.now()

            if (isFollowing) {
                followerRef.delete()
                followingRef.delete()
            } else {
                val followerData = mapOf("userId" to currentUserId, "username" to currentUsername, "followedAt" to now)
                val followingData = mapOf("userId" to targetUserId, "username" to targetUsername, "followedAt" to now)

                followerRef.set(followerData)
                followingRef.set(followingData)
            }
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Error(e.message ?: "Error al seguir/dejar de seguir usuario", e)
        }
    }
}
