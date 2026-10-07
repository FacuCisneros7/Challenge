package org.example.challenge.data.remote

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.firestore

class DatabaseSeeder {
    private val firestore = Firebase.firestore

    suspend fun seedIfEmpty() {
        try {
            val matchesSnapshot = firestore.collection("matches").get()
            if (matchesSnapshot.documents.size >= 1) return

            val sampleMatches = listOf(
                mapOf(
                    "id" to "match_01",
                    "homeTeam" to "Man City",
                    "awayTeam" to "Arsenal",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/e/eb/Manchester_City_FC_badge.svg/500px-Manchester_City_FC_badge.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/5/53/Arsenal_FC.svg/500px-Arsenal_FC.svg.png",
                    "date" to Timestamp(1728000000L, 0),
                    "tags" to listOf("premier", "month"),
                    "stadium" to "Etihad Stadium, Manchester",
                    "homeScore" to 2,
                    "awayScore" to 2
                ),
                mapOf(
                    "id" to "match_02",
                    "homeTeam" to "Liverpool",
                    "awayTeam" to "Chelsea",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/0/0c/Liverpool_FC.svg/500px-Liverpool_FC.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/c/cc/Chelsea_FC.svg/500px-Chelsea_FC.svg.png",
                    "date" to Timestamp(1727913600L, 0),
                    "tags" to listOf("week", "premier", "month"),
                    "stadium" to "Anfield, Liverpool",
                    "homeScore" to 2,
                    "awayScore" to 1
                ),
                mapOf(
                    "id" to "match_03",
                    "homeTeam" to "Man United",
                    "awayTeam" to "Tottenham",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/7/7a/Manchester_United_FC_crest.svg/500px-Manchester_United_FC_crest.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/b/b4/Tottenham_Hotspur.svg/500px-Tottenham_Hotspur.svg.png",
                    "date" to Timestamp(1727827200L, 0),
                    "tags" to listOf("premier"),
                    "stadium" to "Old Trafford, Manchester",
                    "homeScore" to 0,
                    "awayScore" to 3
                ),
                mapOf(
                    "id" to "match_04",
                    "homeTeam" to "Aston Villa",
                    "awayTeam" to "Newcastle",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://thumb.wikimedia.org/wikipedia/fr/thumb/1/14/Aston_Villa_FC_2024.svg/330px-Aston_Villa_FC_2024.svg.png?utm_source=fr.wikipedia.org&utm_campaign=index&utm_content=thumbnail&_=20260318210632",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/5/56/Newcastle_United_Logo.svg/500px-Newcastle_United_Logo.svg.png",
                    "date" to Timestamp(1727740800L, 0),
                    "tags" to listOf("premier", "month"),
                    "stadium" to "Villa Park, Birmingham",
                    "homeScore" to 1,
                    "awayScore" to 0
                ),
                mapOf(
                    "id" to "match_05",
                    "homeTeam" to "Brighton",
                    "awayTeam" to "West Ham",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://thumb.wikimedia.org/wikipedia/fr/thumb/d/dd/Logo_Brighton_%26_Hove_Albion_2024.svg/960px-Logo_Brighton_%26_Hove_Albion_2024.svg.png?utm_source=fr.wikipedia.org&utm_campaign=index&utm_content=thumbnail&_=20241214135311",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/c/c2/West_Ham_United_FC_logo.svg/500px-West_Ham_United_FC_logo.svg.png",
                    "date" to Timestamp(1727654400L, 0),
                    "tags" to listOf("premier"),
                    "stadium" to "Amex Stadium, Brighton",
                    "homeScore" to 1,
                    "awayScore" to 1
                ),
                mapOf(
                    "id" to "match_06",
                    "homeTeam" to "Crystal Palace",
                    "awayTeam" to "Everton",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://thumb.wikimedia.org/wikipedia/fr/thumb/1/1e/Logo_Crystal_Palace_FC_-_2022.svg/500px-Logo_Crystal_Palace_FC_-_2022.svg.png?utm_source=fr.wikipedia.org&utm_campaign=index&utm_content=thumbnail&_=20220620182718",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/7/7c/Everton_FC_logo.svg/500px-Everton_FC_logo.svg.png",
                    "date" to Timestamp(1727568000L, 0),
                    "tags" to listOf("premier"),
                    "stadium" to "Selhurst Park, Londres",
                    "homeScore" to 2,
                    "awayScore" to 0
                ),
                mapOf(
                    "id" to "match_07",
                    "homeTeam" to "Fulham",
                    "awayTeam" to "Brentford",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/e/eb/Fulham_FC_%28shield%29.svg/500px-Fulham_FC_%28shield%29.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/2/2a/Brentford_FC_crest.svg/500px-Brentford_FC_crest.svg.png",
                    "date" to Timestamp(1727481600L, 0),
                    "tags" to listOf("week", "premier"),
                    "stadium" to "Craven Cottage, Londres",
                    "homeScore" to 2,
                    "awayScore" to 1
                ),
                mapOf(
                    "id" to "match_08",
                    "homeTeam" to "Bournemouth",
                    "awayTeam" to "Southampton",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/e/e5/AFC_Bournemouth_%282013%29.svg/500px-AFC_Bournemouth_%282013%29.svg.png",
                    "awayTeamUrl" to "https://thumb.wikimedia.org/wikipedia/fr/thumb/9/91/Logo_Southampton_FC_2011.svg/960px-Logo_Southampton_FC_2011.svg.png?utm_source=fr.wikipedia.org&utm_campaign=index&utm_content=thumbnail&_=20250311163304",
                    "date" to Timestamp(1727395200L, 0),
                    "tags" to listOf("premier"),
                    "stadium" to "Vitality Stadium, Bournemouth",
                    "homeScore" to 3,
                    "awayScore" to 1
                ),
                mapOf(
                    "id" to "match_09",
                    "homeTeam" to "Wolverhampton",
                    "awayTeam" to "Leicester",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://thumb.wikimedia.org/wikipedia/sco/thumb/f/fc/Wolverhampton_Wanderers.svg/330px-Wolverhampton_Wanderers.svg.png?utm_source=sco.wikipedia.org&utm_campaign=index&utm_content=thumbnail&_=20180716172407",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/2/2d/Leicester_City_crest.svg/500px-Leicester_City_crest.svg.png",
                    "date" to Timestamp(1727308800L, 0),
                    "tags" to listOf("premier"),
                    "stadium" to "Molineux Stadium, Wolverhampton",
                    "homeScore" to 2,
                    "awayScore" to 0
                ),
                mapOf(
                    "id" to "match_10",
                    "homeTeam" to "Nottingham Forest",
                    "awayTeam" to "Ipswich Town",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/e/e5/Nottingham_Forest_F.C._logo.svg/500px-Nottingham_Forest_F.C._logo.svg.png",
                    "awayTeamUrl" to "https://thumb.wikimedia.org/wikipedia/fr/thumb/f/ff/Logo_Ipswich_Town_2024.svg/500px-Logo_Ipswich_Town_2024.svg.png?utm_source=fr.wikipedia.org&utm_campaign=index&utm_content=thumbnail&_=20240531221832",
                    "date" to Timestamp(1727222400L, 0),
                    "tags" to listOf("premier"),
                    "stadium" to "City Ground, Nottingham",
                    "homeScore" to 1,
                    "awayScore" to 1
                ),
                mapOf(
                    "id" to "match_11",
                    "homeTeam" to "Arsenal",
                    "awayTeam" to "Liverpool",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/5/53/Arsenal_FC.svg/500px-Arsenal_FC.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/0/0c/Liverpool_FC.svg/500px-Liverpool_FC.svg.png",
                    "date" to Timestamp(1727136000L, 0),
                    "tags" to listOf("premier", "month"),
                    "stadium" to "Emirates Stadium, Londres",
                    "homeScore" to 2,
                    "awayScore" to 2
                ),
                mapOf(
                    "id" to "match_12",
                    "homeTeam" to "Chelsea",
                    "awayTeam" to "Man City",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/c/cc/Chelsea_FC.svg/500px-Chelsea_FC.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/e/eb/Manchester_City_FC_badge.svg/500px-Manchester_City_FC_badge.svg.png",
                    "date" to Timestamp(1727049600L, 0),
                    "tags" to listOf("premier", "month"),
                    "stadium" to "Stamford Bridge, Londres",
                    "homeScore" to 0,
                    "awayScore" to 2
                ),
                mapOf(
                    "id" to "match_14",
                    "homeTeam" to "Newcastle",
                    "awayTeam" to "Man United",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/5/56/Newcastle_United_Logo.svg/500px-Newcastle_United_Logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/7/7a/Manchester_United_FC_crest.svg/500px-Manchester_United_FC_crest.svg.png",
                    "date" to Timestamp(1726876800L, 0),
                    "tags" to listOf("premier"),
                    "stadium" to "St James' Park, Newcastle",
                    "homeScore" to 1,
                    "awayScore" to 0
                ),
                mapOf(
                    "id" to "match_15",
                    "homeTeam" to "West Ham",
                    "awayTeam" to "Fulham",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/c/c2/West_Ham_United_FC_logo.svg/500px-West_Ham_United_FC_logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/e/eb/Fulham_FC_%28shield%29.svg/500px-Fulham_FC_%28shield%29.svg.png",
                    "date" to Timestamp(1726790400L, 0),
                    "tags" to listOf("premier"),
                    "stadium" to "London Stadium, Londres",
                    "homeScore" to 1,
                    "awayScore" to 2
                ),
                mapOf(
                    "id" to "match_16",
                    "homeTeam" to "Everton",
                    "awayTeam" to "Crystal Palace",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/7/7c/Everton_FC_logo.svg/500px-Everton_FC_logo.svg.png",
                    "awayTeamUrl" to "https://thumb.wikimedia.org/wikipedia/fr/thumb/1/1e/Logo_Crystal_Palace_FC_-_2022.svg/500px-Logo_Crystal_Palace_FC_-_2022.svg.png?utm_source=fr.wikipedia.org&utm_campaign=index&utm_content=thumbnail&_=20220620182718",
                    "date" to Timestamp(1726704000L, 0),
                    "tags" to listOf("premier"),
                    "stadium" to "Goodison Park, Liverpool",
                    "homeScore" to 2,
                    "awayScore" to 1
                ),
                mapOf(
                    "id" to "match_17",
                    "homeTeam" to "Brentford",
                    "awayTeam" to "Brighton",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/2/2a/Brentford_FC_crest.svg/500px-Brentford_FC_crest.svg.png",
                    "awayTeamUrl" to "https://thumb.wikimedia.org/wikipedia/fr/thumb/d/dd/Logo_Brighton_%26_Hove_Albion_2024.svg/960px-Logo_Brighton_%26_Hove_Albion_2024.svg.png?utm_source=fr.wikipedia.org&utm_campaign=index&utm_content=thumbnail&_=20241214135311",
                    "date" to Timestamp(1726617600L, 0),
                    "tags" to listOf("premier"),
                    "stadium" to "Gtech Community Stadium, Londres",
                    "homeScore" to 0,
                    "awayScore" to 0
                ),
                mapOf(
                    "id" to "match_19",
                    "homeTeam" to "Leicester",
                    "awayTeam" to "Nottingham Forest",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/2/2d/Leicester_City_crest.svg/500px-Leicester_City_crest.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/e/e5/Nottingham_Forest_F.C._logo.svg/500px-Nottingham_Forest_F.C._logo.svg.png",
                    "date" to Timestamp(1726444800L, 0),
                    "tags" to listOf("premier"),
                    "stadium" to "King Power Stadium, Leicester",
                    "homeScore" to 1,
                    "awayScore" to 3
                ),
                mapOf(
                    "id" to "match_20",
                    "homeTeam" to "Ipswich Town",
                    "awayTeam" to "Bournemouth",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://thumb.wikimedia.org/wikipedia/fr/thumb/f/ff/Logo_Ipswich_Town_2024.svg/500px-Logo_Ipswich_Town_2024.svg.png?utm_source=fr.wikipedia.org&utm_campaign=index&utm_content=thumbnail&_=20240531221832",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/e/e5/AFC_Bournemouth_%282013%29.svg/500px-AFC_Bournemouth_%282013%29.svg.png",
                    "date" to Timestamp(1726358400L, 0),
                    "tags" to listOf("premier"),
                    "stadium" to "Portman Road, Ipswich",
                    "homeScore" to 2,
                    "awayScore" to 2
                ),

                mapOf(
                    "id" to "match_21",
                    "homeTeam" to "Boca Juniors",
                    "awayTeam" to "River Plate",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e3/Boca_Juniors_logo18.svg/500px-Boca_Juniors_logo18.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Club_Atlético_River_Plate_logo.svg/500px-Club_Atlético_River_Plate_logo.svg.png",
                    "date" to Timestamp(1728000000L, 0),
                    "tags" to listOf("week", "month", "year", "argentina"),
                    "stadium" to "La Bombonera, Buenos Aires",
                    "homeScore" to 1,
                    "awayScore" to 0
                ),
                mapOf(
                    "id" to "match_22",
                    "homeTeam" to "Racing Club",
                    "awayTeam" to "Independiente",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Escudo_de_Racing_Club_%282014%29.svg/500px-Escudo_de_Racing_Club_%282014%29.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/d/db/Escudo_del_Club_Atlético_Independiente.svg/500px-Escudo_del_Club_Atlético_Independiente.svg.png",
                    "date" to Timestamp(1727913600L, 0),
                    "tags" to listOf("week", "month", "argentina"),
                    "stadium" to "El Cilindro de Avellaneda, Buenos Aires",
                    "homeScore" to 1,
                    "awayScore" to 1
                ),
                mapOf(
                    "id" to "match_23",
                    "homeTeam" to "San Lorenzo",
                    "awayTeam" to "Huracán",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://thumb.wikimedia.org/wikipedia/commons/thumb/d/d7/Escudo_San_Lorenzo_2026.svg/960px-Escudo_San_Lorenzo_2026.svg.png?utm_source=commons.wikimedia.org&utm_campaign=index&utm_content=thumbnail&_=20260402015652",
                    "awayTeamUrl" to "https://thumb.wikimedia.org/wikipedia/commons/thumb/9/99/Escudo_del_Club_Atl%C3%A9tico_Hurac%C3%A1n.svg/960px-Escudo_del_Club_Atl%C3%A9tico_Hurac%C3%A1n.svg.png?utm_source=commons.wikimedia.org&utm_campaign=imageinfo&utm_content=thumbnail",
                    "date" to Timestamp(1727827200L, 0),
                    "tags" to listOf("week", "argentina"),
                    "stadium" to "Nuevo Gasómetro, Buenos Aires",
                    "homeScore" to 0,
                    "awayScore" to 0
                ),
                mapOf(
                    "id" to "match_24",
                    "homeTeam" to "Estudiantes LP",
                    "awayTeam" to "Gimnasia LP",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Estudiantes_de_la_Plata_crest_%282025%29.svg/500px-Estudiantes_de_la_Plata_crest_%282025%29.svg.png",
                    "awayTeamUrl" to "https://thumb.wikimedia.org/wikipedia/commons/thumb/f/f7/Escudo_del_Club_de_Gimnasia_y_Esgrima_La_Plata_%28v2026%29.svg/960px-Escudo_del_Club_de_Gimnasia_y_Esgrima_La_Plata_%28v2026%29.svg.png?utm_source=commons.wikimedia.org&utm_campaign=imageinfo&utm_content=thumbnail",
                    "date" to Timestamp(1727740800L, 0),
                    "tags" to listOf("argentina", "month"),
                    "stadium" to "Estadio UNO, La Plata",
                    "homeScore" to 4,
                    "awayScore" to 1
                ),
                mapOf(
                    "id" to "match_25",
                    "homeTeam" to "Rosario Central",
                    "awayTeam" to "Newell's Old Boys",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://thumb.wikimedia.org/wikipedia/commons/thumb/8/84/Escudo_del_Club_Atl%C3%A9tico_Rosario_Central.svg/960px-Escudo_del_Club_Atl%C3%A9tico_Rosario_Central.svg.png?utm_source=commons.wikimedia.org&utm_campaign=imageinfo&utm_content=thumbnail",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/b/b0/Newell%27s_Old_Boys_Escudo.png?utm_source=commons.wikimedia.org&utm_campaign=index&utm_content=thumbnail_unscaled&_=20160830001436",
                    "date" to Timestamp(1727654400L, 0),
                    "tags" to listOf("argentina", "month"),
                    "stadium" to "Gigante de Arroyito, Rosario",
                    "homeScore" to 1,
                    "awayScore" to 0
                ),
                mapOf(
                    "id" to "match_26",
                    "homeTeam" to "Talleres",
                    "awayTeam" to "Belgrano",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://thumb.wikimedia.org/wikipedia/commons/thumb/9/9b/Escudo_Talleres_2015.svg/960px-Escudo_Talleres_2015.svg.png?utm_source=commons.wikimedia.org&utm_campaign=imageinfo&utm_content=thumbnail",
                    "awayTeamUrl" to "https://thumb.wikimedia.org/wikipedia/commons/thumb/f/f3/Escudo_del_Club_Atl%C3%A9tico_Belgrano.svg/960px-Escudo_del_Club_Atl%C3%A9tico_Belgrano.svg.png?utm_source=commons.wikimedia.org&utm_campaign=imageinfo&utm_content=thumbnail",
                    "date" to Timestamp(1727568000L, 0),
                    "tags" to listOf("argentina"),
                    "stadium" to "Mario Alberto Kempes, Córdoba",
                    "homeScore" to 2,
                    "awayScore" to 2
                ),
                mapOf(
                    "id" to "match_27",
                    "homeTeam" to "Vélez Sarsfield",
                    "awayTeam" to "Argentinos Juniors",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://thumb.wikimedia.org/wikipedia/commons/thumb/2/21/Escudo_del_Club_Atl%C3%A9tico_V%C3%A9lez_Sarsfield.svg/960px-Escudo_del_Club_Atl%C3%A9tico_V%C3%A9lez_Sarsfield.svg.png?utm_source=commons.wikimedia.org&utm_campaign=imageinfo&utm_content=thumbnail",
                    "awayTeamUrl" to "https://thumb.wikimedia.org/wikipedia/commons/thumb/1/1b/Escudo_de_la_Asociaci%C3%B3n_Atl%C3%A9tica_Argentinos_Juniors.svg/960px-Escudo_de_la_Asociaci%C3%B3n_Atl%C3%A9tica_Argentinos_Juniors.svg.png?utm_source=commons.wikimedia.org&utm_campaign=imageinfo&utm_content=thumbnail",
                    "date" to Timestamp(1727481600L, 0),
                    "tags" to listOf("argentina"),
                    "stadium" to "José Amalfitani, Buenos Aires",
                    "homeScore" to 1,
                    "awayScore" to 0
                ),
                mapOf(
                    "id" to "match_28",
                    "homeTeam" to "Lanús",
                    "awayTeam" to "Banfield",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://thumb.wikimedia.org/wikipedia/commons/thumb/3/38/Escudo_del_CA_Lan%C3%BAs_%28con_7_estrellas_blancas%29.png/960px-Escudo_del_CA_Lan%C3%BAs_%28con_7_estrellas_blancas%29.png?utm_source=commons.wikimedia.org&utm_campaign=imageinfo&utm_content=thumbnail",
                    "awayTeamUrl" to "https://thumb.wikimedia.org/wikipedia/commons/thumb/9/9c/Banfield_2022.png/960px-Banfield_2022.png?utm_source=commons.wikimedia.org&utm_campaign=imageinfo&utm_content=thumbnail",
                    "date" to Timestamp(1727395200L, 0),
                    "tags" to listOf("argentina"),
                    "stadium" to "Ciudad de Lanús, Lanús",
                    "homeScore" to 2,
                    "awayScore" to 1
                ),
                mapOf(
                    "id" to "match_35",
                    "homeTeam" to "River Plate",
                    "awayTeam" to "Boca Juniors",
                    "competition" to "Copa Argentina",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Club_Atlético_River_Plate_logo.svg/500px-Club_Atlético_River_Plate_logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e3/Boca_Juniors_logo18.svg/500px-Boca_Juniors_logo18.svg.png",
                    "date" to Timestamp(1726790400L, 0),
                    "tags" to listOf("argentina", "month"),
                    "stadium" to "Mâs Monumental, Buenos Aires",
                    "homeScore" to 2,
                    "awayScore" to 1
                ),
                mapOf(
                    "id" to "match_36",
                    "homeTeam" to "San Lorenzo",
                    "awayTeam" to "Racing Club",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://thumb.wikimedia.org/wikipedia/commons/thumb/d/d7/Escudo_San_Lorenzo_2026.svg/960px-Escudo_San_Lorenzo_2026.svg.png?utm_source=commons.wikimedia.org&utm_campaign=index&utm_content=thumbnail&_=20260402015652",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Escudo_de_Racing_Club_%282014%29.svg/500px-Escudo_de_Racing_Club_%282014%29.svg.png",
                    "date" to Timestamp(1726704000L, 0),
                    "tags" to listOf("argentina"),
                    "stadium" to "Nuevo Gasómetro, Buenos Aires",
                    "homeScore" to 1,
                    "awayScore" to 2
                ),
                mapOf(
                    "id" to "match_37",
                    "homeTeam" to "Independiente",
                    "awayTeam" to "Estudiantes LP",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/d/db/Escudo_del_Club_Atlético_Independiente.svg/500px-Escudo_del_Club_Atlético_Independiente.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Estudiantes_de_la_Plata_crest_%282025%29.svg/500px-Estudiantes_de_la_Plata_crest_%282025%29.svg.png",
                    "date" to Timestamp(1726617600L, 0),
                    "tags" to listOf("argentina"),
                    "stadium" to "Libertadores de América, Avellaneda",
                    "homeScore" to 0,
                    "awayScore" to 0
                ),
                mapOf(
                    "id" to "match_38",
                    "homeTeam" to "Huracán",
                    "awayTeam" to "Rosario Central",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://thumb.wikimedia.org/wikipedia/commons/thumb/9/99/Escudo_del_Club_Atl%C3%A9tico_Hurac%C3%A1n.svg/960px-Escudo_del_Club_Atl%C3%A9tico_Hurac%C3%A1n.svg.png?utm_source=commons.wikimedia.org&utm_campaign=imageinfo&utm_content=thumbnail",
                    "awayTeamUrl" to "https://thumb.wikimedia.org/wikipedia/commons/thumb/8/84/Escudo_del_Club_Atl%C3%A9tico_Rosario_Central.svg/960px-Escudo_del_Club_Atl%C3%A9tico_Rosario_Central.svg.png?utm_source=commons.wikimedia.org&utm_campaign=imageinfo&utm_content=thumbnail",
                    "date" to Timestamp(1726531200L, 0),
                    "tags" to listOf("argentina"),
                    "stadium" to "Tomás Adolfo Ducó, Buenos Aires",
                    "homeScore" to 2,
                    "awayScore" to 0
                ),
                mapOf(
                    "id" to "match_39",
                    "homeTeam" to "Newell's Old Boys",
                    "awayTeam" to "Talleres",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/b/b0/Newell%27s_Old_Boys_Escudo.png?utm_source=commons.wikimedia.org&utm_campaign=index&utm_content=thumbnail_unscaled&_=20160830001436",
                    "awayTeamUrl" to "https://thumb.wikimedia.org/wikipedia/commons/thumb/9/9b/Escudo_Talleres_2015.svg/960px-Escudo_Talleres_2015.svg.png?utm_source=commons.wikimedia.org&utm_campaign=imageinfo&utm_content=thumbnail",
                    "date" to Timestamp(1726444800L, 0),
                    "tags" to listOf("argentina"),
                    "stadium" to "Coloso del Parque, Rosario",
                    "homeScore" to 1,
                    "awayScore" to 3
                ),
                mapOf(
                    "id" to "match_40",
                    "homeTeam" to "Banfield",
                    "awayTeam" to "Vélez Sarsfield",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://thumb.wikimedia.org/wikipedia/commons/thumb/9/9c/Banfield_2022.png/960px-Banfield_2022.png?utm_source=commons.wikimedia.org&utm_campaign=imageinfo&utm_content=thumbnail",
                    "awayTeamUrl" to "https://thumb.wikimedia.org/wikipedia/commons/thumb/2/21/Escudo_del_Club_Atl%C3%A9tico_V%C3%A9lez_Sarsfield.svg/960px-Escudo_del_Club_Atl%C3%A9tico_V%C3%A9lez_Sarsfield.svg.png?utm_source=commons.wikimedia.org&utm_campaign=imageinfo&utm_content=thumbnail",
                    "date" to Timestamp(1726358400L, 0),
                    "tags" to listOf("argentina"),
                    "stadium" to "Florencio Sola, Banfield",
                    "homeScore" to 1,
                    "awayScore" to 2
                )
            )

            for (m in sampleMatches) {
                val id = m["id"] as String
                val data = m.filterKeys { it != "id" }
                firestore.collection("matches").document(id).set(data)
            }
        } catch (e: Exception) {
            // Seeder errors can be safely ignored
        }
    }
}
