package goshka133.football.domain_auth

import goshka133.football.domain_auth.dto.SessionResponse
import goshka133.football.domain_auth.dto.UpdateSessionRequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {

  @POST("Authentication/phone/{phoneNumber}")
  suspend fun sendOtpCode(@Path("phoneNumber") phoneNumber: String)

  @POST("Authentication/refresh/{phoneNumber}/{code}")
  suspend fun verifyOtpCode(
    @Path("phoneNumber") phoneNumber: String,
    @Path("code") code: String,
  ): SessionResponse

  @POST("/api/Authentication/access")
  suspend fun updateSession(
    @Body requestBody: UpdateSessionRequestBody,
  ): SessionResponse
}
