package org.example.challenge.data.remote

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.firestore

class DatabaseSeeder {
    private val firestore = Firebase.firestore

    suspend fun seedIfEmpty() {
        try {
            val matchesSnapshot = firestore.collection("matches").get()
            if (matchesSnapshot.documents.isNotEmpty()) return

            val sampleMatches = listOf(
                mapOf(
                    "id" to "match_01",
                    "homeTeam" to "Boca Juniors",
                    "awayTeam" to "River Plate",
                    "competition" to "Liga Profesional",
                    "imageUrl" to "https://images.unsplash.com/photo-1508098682722-e99c43a406b2?w=600",
                    "date" to Timestamp(1728000000L, 0),
                    "tags" to listOf("week", "month", "year", "argentina"),
                    "stadium" to "La Bombonera, Buenos Aires",
                    "homeScore" to 1,
                    "awayScore" to 0
                ),
                mapOf(
                    "id" to "match_02",
                    "homeTeam" to "Real Madrid",
                    "awayTeam" to "FC Barcelona",
                    "competition" to "La Liga",
                    "imageUrl" to "https://images.unsplash.com/photo-1574629810360-7efbbe195018?w=600",
                    "date" to Timestamp(1727913600L, 0),
                    "tags" to listOf("week", "month", "champions"),
                    "stadium" to "Santiago Bernabéu, Madrid",
                    "homeScore" to 3,
                    "awayScore" to 2
                ),
                mapOf(
                    "id" to "match_03",
                    "homeTeam" to "Manchester City",
                    "awayTeam" to "Arsenal",
                    "competition" to "Premier League",
                    "imageUrl" to "https://images.unsplash.com/photo-1522778119026-d647f0596c20?w=600",
                    "date" to Timestamp(1727827200L, 0),
                    "tags" to listOf("week", "premier", "month"),
                    "stadium" to "Etihad Stadium, Manchester",
                    "homeScore" to 2,
                    "awayScore" to 2
                ),
                mapOf(
                    "id" to "match_04",
                    "homeTeam" to "Argentina",
                    "awayTeam" to "Francia",
                    "competition" to "Mundial 2022",
                    "imageUrl" to "https://images.unsplash.com/photo-1518091043644-c1d4457512c6?w=600",
                    "date" to Timestamp(1727740800L, 0),
                    "tags" to listOf("year", "argentina"),
                    "stadium" to "Lusail Iconic Stadium, Catar",
                    "homeScore" to 3,
                    "awayScore" to 3
                ),
                mapOf(
                    "id" to "match_05",
                    "homeTeam" to "Liverpool",
                    "awayTeam" to "Chelsea",
                    "competition" to "Premier League",
                    "imageUrl" to "https://images.unsplash.com/photo-1511886929837-354d827aae26?w=600",
                    "date" to Timestamp(1727654400L, 0),
                    "tags" to listOf("premier", "month"),
                    "stadium" to "Anfield, Liverpool",
                    "homeScore" to 1,
                    "awayScore" to 1
                ),
                mapOf(
                    "id" to "match_06",
                    "homeTeam" to "Racing Club",
                    "awayTeam" to "Independiente",
                    "competition" to "Liga Profesional",
                    "imageUrl" to "https://images.unsplash.com/photo-1543326727-cf6c39e8f84c?w=600",
                    "date" to Timestamp(1727568000L, 0),
                    "tags" to listOf("argentina", "month"),
                    "stadium" to "El Cilindro de Avellaneda, Buenos Aires",
                    "homeScore" to 1,
                    "awayScore" to 0
                )
            )

            for (m in sampleMatches) {
                val id = m["id"] as String
                val data = m.filterKeys { it != "id" }
                firestore.collection("matches").document(id).set(data)
            }

            val sampleReviews = listOf(
                mapOf(
                    "matchId" to "match_01",
                    "userId" to "sample_user_1",
                    "username" to "Futbolero99",
                    "rating" to 5,
                    "text" to "¡Qué partidazo memorable! El Superclásico nunca defrauda.",
                    "createdAt" to Timestamp(1728003600L, 0),
                    "updatedAt" to Timestamp(1728003600L, 0)
                ),
                mapOf(
                    "matchId" to "match_02",
                    "userId" to "sample_user_2",
                    "username" to "TacticoTactico",
                    "rating" to 4,
                    "text" to "Gran despliegue táctico en el segundo tiempo.",
                    "createdAt" to Timestamp(1727917200L, 0),
                    "updatedAt" to Timestamp(1727917200L, 0)
                )
            )

            for (r in sampleReviews) {
                val reviewId = "${r["userId"]}_${r["matchId"]}"
                firestore.collection("reviews").document(reviewId).set(r)
            }
        } catch (e: Exception) {
            // Seeder errors can be safely ignored
        }
    }
}
