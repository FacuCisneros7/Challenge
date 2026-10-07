package org.example.challenge

import kotlinx.datetime.Instant
import org.example.challenge.domain.model.Match
import kotlin.test.Test
import kotlin.test.assertEquals

class MatchTest {

    @Test
    fun testFormattedScoreWhenScoresArePresent() {
        val match = Match(
            id = "1",
            homeTeam = "Boca Juniors",
            awayTeam = "River Plate",
            competition = "Superclásico",
            date = Instant.DISTANT_PAST,
            tags = emptyList(),
            homeScore = 2,
            awayScore = 1
        )
        assertEquals("2 - 1", match.formattedScore)
    }

    @Test
    fun testFormattedScoreWhenScoresAreNull() {
        val match = Match(
            id = "1",
            homeTeam = "Boca Juniors",
            awayTeam = "River Plate",
            competition = "Superclásico",
            date = Instant.DISTANT_PAST,
            tags = emptyList(),
            homeScore = null,
            awayScore = null
        )
        assertEquals("", match.formattedScore)
    }
}
