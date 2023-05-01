package andryuh.football.domain_profile

import andryuh.football.domain_profile.dto.CreateProfileBody
import andryuh.football.domain_profile.dto.Profile
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ProfileApi {

  @POST("Players") suspend fun createProfile(@Body profileBody: CreateProfileBody)

  @GET("Players/{phoneNumber}")
  suspend fun getProfile(@Path("phoneNumber") phoneNumber: String): Profile

  @GET("Players")
  suspend fun getAllProfile(): List<Profile>

  @PUT("Players/{phoneNumber}")
  suspend fun updateProfile(
    @Path("phoneNumber") phoneNumber: String,
    @Body profileBody: Profile
  ): Response<Unit>

  @Multipart
  @POST("Image/Player/{phoneNumber}")
  suspend fun updatePhoto(
    @Path("phoneNumber") phoneNumber: String,
    @Part image: MultipartBody.Part,
  ): Response<Unit>
}
