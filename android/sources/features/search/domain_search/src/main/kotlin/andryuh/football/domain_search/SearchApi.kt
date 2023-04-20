package andryuh.football.domain_search

import andryuh.football.domain_profile.dto.PlayerApplication
import andryuh.football.domain_search.dto.CreatePlayerApplication
import andryuh.football.domain_team.dto.TeamApplication
import andryuh.football.domain_team.dto.TeamApplicationCreation
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SearchApi {

  @GET("TeamApplications") suspend fun getTeamApplications(): List<TeamApplication>

  @POST("TeamApplications")
  suspend fun createTeamApplication(@Body body: TeamApplicationCreation): Response<Unit>

  @POST("PlayerApplications")
  suspend fun createPlayerApplication(@Body body: CreatePlayerApplication): Response<Unit>

  @GET("PlayerApplications") suspend fun getPlayerApplications(): List<PlayerApplication>
}
