package goshka133.football.core_auth.session

import goshka133.football.core_auth.dto.UpdateUserSessionRequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface UserSessionApi {

  @POST("/api/Authentication/access")
  suspend fun updateSession(
    @Body requestBody: UpdateUserSessionRequestBody,
  ): UserSession
}
