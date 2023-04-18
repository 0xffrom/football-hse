package andryuh.football.domain_search

import andryuh.football.domain_team.dto.TeamApplication
import retrofit2.http.GET

interface SearchApi {

  @GET("TeamApplications") suspend fun getTeamApplications(): List<TeamApplication>
}
