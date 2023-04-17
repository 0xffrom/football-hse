package andryuh.football.domain_team

import andryuh.football.domain_team.dto.CreateTeamRequestBody
import andryuh.football.domain_team.dto.Team
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface TeamApi {

  @POST("Teams") suspend fun createTeam(@Body body: CreateTeamRequestBody): Response<Unit>

  @GET("Teams") suspend fun getTeams(): List<Team>

  @Multipart
  @POST("Image/Team/{teamId}")
  suspend fun updatePhoto(
    @Path("teamId") teamId: String,
    @Part image: MultipartBody.Part,
  ): Response<Unit>
}
