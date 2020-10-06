package by.footballcompetition.data

import java.io.Serializable

data class Teams(
      val count: Int,
      val filters: Filters,
      val teams: List<Team>
)

data class Team(
      val address: String,
      val area: Area,
      val clubColors: String,
      val crestUrl: String,
      val email: String,
      val founded: Int?,
      val id: Int,
      val lastUpdated: String,
      val name: String,
      val phone: String,
      val shortName: String,
      val tla: String,
      val venue: String,
      val website: String?
) : Serializable

data class Filters(
      val areas: List<Int>,
      val permission: String
)

data class Area(
      val id: Int,
      val name: String
) : Serializable