package org.example.challenge.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.firestore
import kotlinx.datetime.Instant
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
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
                val timestamp = doc.get("date", Timestamp.serializer().nullable)
                val instant =
                    timestamp?.let { Instant.fromEpochSeconds(it.seconds, it.nanoseconds) }
                        ?: Instant.DISTANT_PAST

                Match(
                    id = doc.id,
                    homeTeam = doc.get("homeTeam", String.serializer().nullable) ?: "",
                    awayTeam = doc.get("awayTeam", String.serializer().nullable) ?: "",
                    homeTeamUrl = doc.get("homeTeamUrl", String.serializer().nullable) ?: "",
                    awayTeamUrl = doc.get("awayTeamUrl", String.serializer().nullable) ?: "",
                    competition = doc.get("competition", String.serializer().nullable) ?: "",
                    date = instant,
                    tags = doc.get("tags", ListSerializer(String.serializer()).nullable)
                        ?: emptyList(),
                    stadium = doc.get("stadium", String.serializer().nullable) ?: "",
                    homeScore = doc.get("homeScore", Int.serializer().nullable),
                    awayScore = doc.get("awayScore", Int.serializer().nullable)
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
            val timestamp = doc.get("date", Timestamp.serializer().nullable)
            val instant = timestamp?.let { Instant.fromEpochSeconds(it.seconds, it.nanoseconds) }
                ?: Instant.DISTANT_PAST

            val match = Match(
                id = doc.id,
                homeTeam = doc.get("homeTeam", String.serializer().nullable) ?: "",
                awayTeam = doc.get("awayTeam", String.serializer().nullable) ?: "",
                homeTeamUrl = doc.get("homeTeamUrl", String.serializer().nullable) ?: "",
                awayTeamUrl = doc.get("awayTeamUrl", String.serializer().nullable) ?: "",
                competition = doc.get("competition", String.serializer().nullable) ?: "",
                date = instant,
                tags = doc.get("tags", ListSerializer(String.serializer()).nullable) ?: emptyList(),
                stadium = doc.get("stadium", String.serializer().nullable) ?: "",
                homeScore = doc.get("homeScore", Int.serializer().nullable),
                awayScore = doc.get("awayScore", Int.serializer().nullable)
            )
            AppResult.Success(match)
        } catch (e: Exception) {
            AppResult.Error(e.message ?: "Error al obtener el detalle del partido", e)
        }
    }
}
