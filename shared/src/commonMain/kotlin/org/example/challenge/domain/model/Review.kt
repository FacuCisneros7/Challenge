package org.example.challenge.domain.model

import kotlinx.datetime.Instant

data class Review(
    val id: String,
    val matchId: String,
    val userId: String,
    val username: String,
    val rating: Int,
    val text: String,
    val createdAt: Instant,
    val updatedAt: Instant? = null
)
