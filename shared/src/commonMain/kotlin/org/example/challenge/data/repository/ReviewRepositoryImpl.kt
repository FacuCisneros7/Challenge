package org.example.challenge.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.firestore
import kotlinx.datetime.Instant
import org.example.challenge.domain.model.AppResult
import org.example.challenge.domain.model.Review
import org.example.challenge.domain.repository.ReviewRepository

class ReviewRepositoryImpl : ReviewRepository {
    private val firestore = Firebase.firestore

    override suspend fun getReviewsForMatch(matchId: String): AppResult<List<Review>> {
        return try {
            val snapshot = firestore.collection("reviews")
                .where { "matchId" equalTo matchId }
                .get()

            val reviews = snapshot.documents.map { doc ->
                mapDocumentToReview(doc)
            }.sortedByDescending { it.createdAt }

            AppResult.Success(reviews)
        } catch (e: Exception) {
            AppResult.Error(e.message ?: "Error al obtener reseñas del partido", e)
        }
    }

    override suspend fun getReviewsByUser(userId: String): AppResult<List<Review>> {
        return try {
            val snapshot = firestore.collection("reviews")
                .where { "userId" equalTo userId }
                .get()

            val reviews = snapshot.documents.map { doc ->
                mapDocumentToReview(doc)
            }.sortedByDescending { it.createdAt }

            AppResult.Success(reviews)
        } catch (e: Exception) {
            AppResult.Error(e.message ?: "Error al obtener reseñas del usuario", e)
        }
    }

    override suspend fun saveReview(review: Review): AppResult<Unit> {
        return try {
            val reviewId = review.id.ifBlank { "${review.userId}_${review.matchId}" }
            val now = Timestamp.now()
            val data = mapOf(
                "matchId" to review.matchId,
                "userId" to review.userId,
                "username" to review.username,
                "rating" to review.rating,
                "text" to review.text,
                "createdAt" to Timestamp(review.createdAt.epochSeconds, review.createdAt.nanosecondsOfSecond),
                "updatedAt" to now
            )
            firestore.collection("reviews").document(reviewId).set(data)
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Error(e.message ?: "Error al guardar la reseña", e)
        }
    }

    override suspend fun deleteReview(reviewId: String): AppResult<Unit> {
        return try {
            firestore.collection("reviews").document(reviewId).delete()
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Error(e.message ?: "Error al eliminar la reseña", e)
        }
    }

    private fun mapDocumentToReview(doc: DocumentSnapshot): Review {
        val createdTs = doc.get<Timestamp?>("createdAt")
        val updatedTs = doc.get<Timestamp?>("updatedAt")
        val createdInstant = createdTs?.let { Instant.fromEpochSeconds(it.seconds, it.nanoseconds) } ?: Instant.DISTANT_PAST
        val updatedInstant = updatedTs?.let { Instant.fromEpochSeconds(it.seconds, it.nanoseconds) }

        val ratingVal = doc.get<Number?>("rating")?.toInt() ?: 0

        return Review(
            id = doc.id,
            matchId = doc.get<String>("matchId"),
            userId = doc.get<String>("userId"),
            username = doc.get<String>("username"),
            rating = ratingVal,
            text = doc.get<String>("text"),
            createdAt = createdInstant,
            updatedAt = updatedInstant
        )
    }
}
