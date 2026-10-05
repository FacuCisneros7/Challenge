package org.example.challenge.domain.repository

import org.example.challenge.domain.model.AppResult
import org.example.challenge.domain.model.Match

/**
 * Repository interface for managing football matches.
 */
interface MatchRepository {
    suspend fun getMatchesByTag(tag: String): AppResult<List<Match>>

    suspend fun getMatch(id: String): AppResult<Match?>
}
