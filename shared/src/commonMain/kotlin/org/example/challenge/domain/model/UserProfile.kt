package org.example.challenge.domain.model

import kotlinx.datetime.Instant

data class UserProfile(
    val userId: String,
    val username: String,
    val bio: String,
    val createdAt: Instant,
    val favorites: List<String> = emptyList(),
    val followers: List<UserProfile> = emptyList(),
    val following: List<UserProfile> = emptyList(),
    val isDarkMode: Boolean = true
) {
    val followersCount: Int get() = followers.size
    val followingCount: Int get() = following.size
}
