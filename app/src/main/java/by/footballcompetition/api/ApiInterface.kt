package by.footballcompetition.api

import by.footballcompetition.data.Competitions
import by.footballcompetition.data.StandingData
import by.footballcompetition.data.Teams
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException

interface ApiInterface {

    @GET("v2/teams")
    @Throws(IOException::class)
    suspend fun getTeams(): Teams

    @GET("v2/competitions")
    @Throws(IOException::class)
    suspend fun getCompetitions(): Competitions

    @GET("v2/competitions/{competitionCode}/standings")
    @Throws(IOException::class)
    suspend fun getStanding(@Path("competitionCode") competitionCode: String): StandingData
}