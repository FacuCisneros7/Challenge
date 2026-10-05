package org.example.challenge.domain.repository

import org.example.challenge.domain.model.AppResult
import org.example.challenge.domain.model.Review

/**
 * Repository interface for managing match reviews.
 */
interface ReviewRepository {
    suspend fun getReviewsForMatch(matchId: String): AppResult<List<Review>>

    suspend fun getReviewsByUser(userId: String): AppResult<List<Review>>

    suspend fun saveReview(review: Review): AppResult<Unit>

    suspend fun deleteReview(reviewId: String): AppResult<Unit>
}
