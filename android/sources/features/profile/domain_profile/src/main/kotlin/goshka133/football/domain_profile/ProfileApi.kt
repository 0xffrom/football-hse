package goshka133.football.domain_profile

import goshka133.football.domain_profile.dto.CreateProfileBody
import goshka133.football.domain_profile.dto.Profile
import retrofit2.Response
import retrofit2.http.*

interface ProfileApi {

  @POST("Players") suspend fun createProfile(@Body profileBody: CreateProfileBody)

  @GET("Players/{phoneNumber}")
  suspend fun getProfile(@Path("phoneNumber") phoneNumber: String): Profile

  @PUT("Players/{phoneNumber}")
  suspend fun updateProfile(
    @Path("phoneNumber") phoneNumber: String,
    @Body profileBody: Profile
  ): Response<Unit>
}
