package org.example.challenge.domain.model

import kotlinx.datetime.Instant

data class UserProfile(
    val userId: String,
    val username: String,
    val bio: String,
    val createdAt: Instant,
    val favorites: List<String> = emptyList()
)
