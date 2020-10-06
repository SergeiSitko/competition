package by.footballcompetition.data

data class Competitions(
      val competitions: List<Competition>,
      val count: Int,
      val filters: Filters
)

data class Competition(
      val area: Area,
      val code: String?,
      val currentSeason: CurrentSeason,
      val emblemUrl: Any,
      val id: Int,
      val lastUpdated: String,
      val name: String,
      val numberOfAvailableSeasons: Int,
      val plan: String
)

data class CurrentSeason(
      val currentMatchday: Any,
      val endDate: String,
      val id: Int,
      val startDate: String,
      val winner: Any
)