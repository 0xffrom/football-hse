package andryuh.football.domain_search

import andryuh.football.domain_profile.dto.PlayerApplication
import andryuh.football.domain_search.dto.CreatePlayerApplication
import andryuh.football.domain_team.dto.TeamApplication
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SearchApi {

  @GET("TeamApplications") suspend fun getTeamApplications(): List<TeamApplication>

  @POST("PlayerApplications")
  suspend fun createPlayerApplication(@Body body: CreatePlayerApplication): Response<Unit>

  @GET("PlayerApplications")
  suspend fun getPlayerApplications(): List<PlayerApplication>
}
