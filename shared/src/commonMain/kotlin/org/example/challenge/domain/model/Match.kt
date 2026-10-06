package org.example.challenge.domain.model

import kotlinx.datetime.Instant

data class Match(
    val id: String,
    val homeTeam: String,
    val awayTeam: String,
    val homeTeamUrl: String = "",
    val awayTeamUrl: String = "",
    val competition: String,
    val date: Instant,
    val tags: List<String>,
    val stadium: String = "",
    val homeScore: Int? = null,
    val awayScore: Int? = null
) {
    val formattedScore: String
        get() = if (homeScore != null && awayScore != null) "$homeScore - $awayScore" else ""
}
