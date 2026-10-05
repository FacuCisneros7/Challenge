package org.example.challenge.domain.model

import kotlinx.datetime.Instant

data class Match(
    val id: String,
    val homeTeam: String,
    val awayTeam: String,
    val imageUrl: String,
    val competition: String,
    val date: Instant,
    val tags: List<String>
)
