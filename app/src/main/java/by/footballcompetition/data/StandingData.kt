package by.footballcompetition.data

data class StandingData(
      val competition: Competition,
      val filters: Filters,
      val season: Season,
      val standings: List<Standing>
)

data class Standing(
      val group: Any,
      val stage: String,
      val table: List<Table>,
      val type: String
)

data class Season(
      val currentMatchday: Int,
      val endDate: String,
      val id: Int,
      val startDate: String,
      val winner: Any
)

data class Table(
      val draw: Int,
      val form: Any,
      val goalDifference: Int,
      val goalsAgainst: Int,
      val goalsFor: Int,
      val lost: Int,
      val playedGames: Int,
      val points: Int,
      val position: Int,
      val team: Team,
      val won: Int
)