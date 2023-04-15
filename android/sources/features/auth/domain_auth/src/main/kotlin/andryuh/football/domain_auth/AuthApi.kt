package andryuh.football.domain_auth

import andryuh.football.core_auth.session.UserSession
import andryuh.football.domain_auth.dto.UserSessionResponse
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {

  @POST("Authentication/phone/{phoneNumber}")
  suspend fun sendOtpCode(@Path("phoneNumber") phoneNumber: String)

  @POST("Authentication/refresh/{phoneNumber}/{code}")
  suspend fun verifyOtpCode(
    @Path("phoneNumber") phoneNumber: String,
    @Path("code") code: String,
  ): UserSession
}
