package org.example.challenge.data.remote

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.firestore

class DatabaseSeeder {
    private val firestore = Firebase.firestore

    suspend fun seedIfEmpty() {
        try {
            val matchesSnapshot = firestore.collection("matches").get()
            if (matchesSnapshot.documents.size >= 40) return

            val sampleMatches = listOf(
                // --- PREMIER LEAGUE (20 matches) ---
                mapOf(
                    "id" to "match_01",
                    "homeTeam" to "Man City",
                    "awayTeam" to "Arsenal",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/e/eb/Manchester_City_FC_badge.svg/500px-Manchester_City_FC_badge.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/5/53/Arsenal_FC.svg/500px-Arsenal_FC.svg.png",
                    "date" to Timestamp(1728000000L, 0),
                    "tags" to listOf("week", "premier", "month"),
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
                    "tags" to listOf("week", "premier"),
                    "stadium" to "Old Trafford, Manchester",
                    "homeScore" to 0,
                    "awayScore" to 3
                ),
                mapOf(
                    "id" to "match_04",
                    "homeTeam" to "Aston Villa",
                    "awayTeam" to "Newcastle",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/f/f9/Aston_Villa_FC_crest_%282016%29.svg/500px-Aston_Villa_FC_crest_%282016%29.svg.png",
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
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/f/fd/Brighton_%26_Hove_Albion_logo.svg/500px-Brighton_%26_Hove_Albion_logo.svg.png",
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
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/e/e2/Crystal_Palace_FC_logo_%282022%29.svg/500px-Crystal_Palace_FC_logo_%282022%29.svg.png",
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
                    "tags" to listOf("premier"),
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
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/c/c9/Southampton_FC.svg/500px-Southampton_FC.svg.png",
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
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/f/fc/Wolverhampton_Wanderers_FC.svg/500px-Wolverhampton_Wanderers_FC.svg.png",
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
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/4/43/Ipswich_Town_FC_logo_%282024%29.svg/500px-Ipswich_Town_FC_logo_%282024%29.svg.png",
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
                    "id" to "match_13",
                    "homeTeam" to "Tottenham",
                    "awayTeam" to "Aston Villa",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/b/b4/Tottenham_Hotspur.svg/500px-Tottenham_Hotspur.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/f/f9/Aston_Villa_FC_crest_%282016%29.svg/500px-Aston_Villa_FC_crest_%282016%29.svg.png",
                    "date" to Timestamp(1726963200L, 0),
                    "tags" to listOf("premier"),
                    "stadium" to "Tottenham Hotspur Stadium, Londres",
                    "homeScore" to 4,
                    "awayScore" to 1
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
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/e/e2/Crystal_Palace_FC_logo_%282022%29.svg/500px-Crystal_Palace_FC_logo_%282022%29.svg.png",
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
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/f/fd/Brighton_%26_Hove_Albion_logo.svg/500px-Brighton_%26_Hove_Albion_logo.svg.png",
                    "date" to Timestamp(1726617600L, 0),
                    "tags" to listOf("premier"),
                    "stadium" to "Gtech Community Stadium, Londres",
                    "homeScore" to 0,
                    "awayScore" to 0
                ),
                mapOf(
                    "id" to "match_18",
                    "homeTeam" to "Southampton",
                    "awayTeam" to "Wolverhampton",
                    "competition" to "Premier League",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/c/c9/Southampton_FC.svg/500px-Southampton_FC.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/f/fc/Wolverhampton_Wanderers_FC.svg/500px-Wolverhampton_Wanderers_FC.svg.png",
                    "date" to Timestamp(1726531200L, 0),
                    "tags" to listOf("premier"),
                    "stadium" to "St Mary's Stadium, Southampton",
                    "homeScore" to 0,
                    "awayScore" to 2
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
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/4/43/Ipswich_Town_FC_logo_%282024%29.svg/500px-Ipswich_Town_FC_logo_%282024%29.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/en/thumb/e/e5/AFC_Bournemouth_%282013%29.svg/500px-AFC_Bournemouth_%282013%29.svg.png",
                    "date" to Timestamp(1726358400L, 0),
                    "tags" to listOf("premier"),
                    "stadium" to "Portman Road, Ipswich",
                    "homeScore" to 2,
                    "awayScore" to 2
                ),

                // --- LIGA ARGENTINA (20 matches) ---
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
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/San_Lorenzo_de_Almagro_logo.svg/500px-San_Lorenzo_de_Almagro_logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Club_Atlético_Huracán_logo.svg/500px-Club_Atlético_Huracán_logo.svg.png",
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
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/Club_de_Gimnasia_y_Esgrima_La_Plata_logo.svg/500px-Club_de_Gimnasia_y_Esgrima_La_Plata_logo.svg.png",
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
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Rosario_Central_logo.svg/500px-Rosario_Central_logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/6/62/Newells_Old_Boys_logo.svg/500px-Newells_Old_Boys_logo.svg.png",
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
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Talleres_de_Cordoba_logo.svg/500px-Talleres_de_Cordoba_logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/6/61/Club_Atlethic_Belgrano_logo.svg/500px-Club_Atlethic_Belgrano_logo.svg.png",
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
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1d/Velez_Sarsfield_logo.svg/500px-Vélez_Sarsfield_logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/Asociación_Atlética_Argentinos_Juniors_logo.svg/500px-Asociación_Atlética_Argentinos_Juniors_logo.svg.png",
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
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/Club_Atletico_Lanus_logo.svg/500px-Club_Atletico_Lanus_logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Club_Atletico_Banfield_logo.svg/500px-Club_Atletico_Banfield_logo.svg.png",
                    "date" to Timestamp(1727395200L, 0),
                    "tags" to listOf("argentina"),
                    "stadium" to "Ciudad de Lanús, Lanús",
                    "homeScore" to 2,
                    "awayScore" to 1
                ),
                mapOf(
                    "id" to "match_29",
                    "homeTeam" to "Defensa y Justicia",
                    "awayTeam" to "Platense",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/5/54/Defensa_y_Justicia_logo.svg/500px-Defensa_y_Justicia_logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/8/87/Club_Atletico_Platense_logo.svg/500px-Club_Atletico_Platense_logo.svg.png",
                    "date" to Timestamp(1727308800L, 0),
                    "tags" to listOf("argentina"),
                    "stadium" to "Norberto Tomaghello, Florencio Varela",
                    "homeScore" to 1,
                    "awayScore" to 0
                ),
                mapOf(
                    "id" to "match_30",
                    "homeTeam" to "Atlético Tucumán",
                    "awayTeam" to "Godoy Cruz",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/Club_Atlethic_Tucuman_logo.svg/500px-Club_Atlethic_Tucuman_logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e3/Godoy_Cruz_Antonio_Tomba_logo.svg/500px-Godoy_Cruz_Antonio_Tomba_logo.svg.png",
                    "date" to Timestamp(1727222400L, 0),
                    "tags" to listOf("argentina"),
                    "stadium" to "Monumental José Fierro, Tucumán",
                    "homeScore" to 2,
                    "awayScore" to 1
                ),
                mapOf(
                    "id" to "match_31",
                    "homeTeam" to "Central Córdoba",
                    "awayTeam" to "Unión de Santa Fe",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Central_Cordoba_de_Santiago_del_Estero_logo.svg/500px-Central_Cordoba_de_Santiago_del_Estero_logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/3/32/Club_Atlethic_Union_logo.svg/500px-Club_Atlethic_Union_logo.svg.png",
                    "date" to Timestamp(1727136000L, 0),
                    "tags" to listOf("argentina"),
                    "stadium" to "Madre de Ciudades, Santiago del Estero",
                    "homeScore" to 0,
                    "awayScore" to 2
                ),
                mapOf(
                    "id" to "match_32",
                    "homeTeam" to "Tigre",
                    "awayTeam" to "Barracas Central",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/Club_Atletico_Tigre_logo.svg/500px-Club_Atletico_Tigre_logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f0/Club_Atlético_Barracas_Central_logo.svg/500px-Club_Atlético_Barracas_Central_logo.svg.png",
                    "date" to Timestamp(1727049600L, 0),
                    "tags" to listOf("argentina"),
                    "stadium" to "José Dellagiovanna, Victoria",
                    "homeScore" to 3,
                    "awayScore" to 0
                ),
                mapOf(
                    "id" to "match_33",
                    "homeTeam" to "Instituto",
                    "awayTeam" to "Sarmiento",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/8/87/Instituto_Atletico_Central_Cordoba_logo.svg/500px-Instituto_Atletico_Central_Cordoba_logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/Club_Atletico_Sarmiento_logo.svg/500px-Club_Atletico_Sarmiento_logo.svg.png",
                    "date" to Timestamp(1726963200L, 0),
                    "tags" to listOf("argentina"),
                    "stadium" to "Monumental de Alta Gracia, Córdoba",
                    "homeScore" to 1,
                    "awayScore" to 1
                ),
                mapOf(
                    "id" to "match_34",
                    "homeTeam" to "Independiente Rivadavia",
                    "awayTeam" to "Riestra",
                    "competition" to "Liga Profesional",
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/3/36/Independiente_Rivadavia_logo.svg/500px-Independiente_Rivadavia_logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Deportivo_Riestra_logo.svg/500px-Deportivo_Riestra_logo.svg.png",
                    "date" to Timestamp(1726876800L, 0),
                    "tags" to listOf("argentina"),
                    "stadium" to "Bautista Gargantini, Mendoza",
                    "homeScore" to 0,
                    "awayScore" to 0
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
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/San_Lorenzo_de_Almagro_logo.svg/500px-San_Lorenzo_de_Almagro_logo.svg.png",
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
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Club_Atlético_Huracán_logo.svg/500px-Club_Atlético_Huracán_logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Rosario_Central_logo.svg/500px-Rosario_Central_logo.svg.png",
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
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/6/62/Newells_Old_Boys_logo.svg/500px-Newells_Old_Boys_logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Talleres_de_Cordoba_logo.svg/500px-Talleres_de_Cordoba_logo.svg.png",
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
                    "homeTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Club_Atletico_Banfield_logo.svg/500px-Club_Atletico_Banfield_logo.svg.png",
                    "awayTeamUrl" to "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1d/Velez_Sarsfield_logo.svg/500px-Vélez_Sarsfield_logo.svg.png",
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
