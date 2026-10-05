package org.example.challenge.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.firestore
import kotlinx.datetime.Instant
import org.example.challenge.domain.model.AppResult
import org.example.challenge.domain.model.Match
import org.example.challenge.domain.repository.MatchRepository

class MatchRepositoryImpl : MatchRepository {
    private val firestore = Firebase.firestore

    override suspend fun getMatchesByTag(tag: String): AppResult<List<Match>> {
        return try {
            val snapshot = firestore.collection("matches")
                .where { "tags" contains tag }
                .get()

            val matches = snapshot.documents.map { doc ->
                val timestamp = doc.get<Timestamp?>("date")
                val instant = timestamp?.let { Instant.fromEpochSeconds(it.seconds, it.nanoseconds) } ?: Instant.DISTANT_PAST

                Match(
                    id = doc.id,
                    homeTeam = doc.get<String>("homeTeam"),
                    awayTeam = doc.get<String>("awayTeam"),
                    imageUrl = doc.get<String>("imageUrl"),
                    competition = doc.get<String>("competition"),
                    date = instant,
                    tags = doc.get<List<String>?>("tags") ?: emptyList(),
                    stadium = doc.get<String?>("stadium") ?: "",
                    homeScore = doc.get<Number?>("homeScore")?.toInt(),
                    awayScore = doc.get<Number?>("awayScore")?.toInt()
                )
            }
            AppResult.Success(matches)
        } catch (e: Exception) {
            AppResult.Error(e.message ?: "Error al obtener partidos por etiqueta", e)
        }
    }

    override suspend fun getMatch(id: String): AppResult<Match?> {
        return try {
            val doc = firestore.collection("matches").document(id).get()
            if (!doc.exists) {
                return AppResult.Success(null)
            }
            val timestamp = doc.get<Timestamp?>("date")
            val instant = timestamp?.let { Instant.fromEpochSeconds(it.seconds, it.nanoseconds) } ?: Instant.DISTANT_PAST

            val match = Match(
                id = doc.id,
                homeTeam = doc.get<String>("homeTeam"),
                awayTeam = doc.get<String>("awayTeam"),
                imageUrl = doc.get<String>("imageUrl"),
                competition = doc.get<String>("competition"),
                date = instant,
                tags = doc.get<List<String>?>("tags") ?: emptyList(),
                stadium = doc.get<String?>("stadium") ?: "",
                homeScore = doc.get<Number?>("homeScore")?.toInt(),
                awayScore = doc.get<Number?>("awayScore")?.toInt()
            )
            AppResult.Success(match)
        } catch (e: Exception) {
            AppResult.Error(e.message ?: "Error al obtener el detalle del partido", e)
        }
    }
}
