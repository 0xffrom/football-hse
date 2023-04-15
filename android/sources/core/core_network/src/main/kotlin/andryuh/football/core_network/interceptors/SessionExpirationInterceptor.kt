package andryuh.football.core_network.interceptors

import andryuh.football.core_auth.session.UserSessionUpdater
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

internal class SessionExpirationInterceptor
@Inject
constructor(
  private val sessionUpdater: UserSessionUpdater,
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val response = chain.proceed(request)

    if (response.code == 401) {
      runBlocking { sessionUpdater.updateSession() }

      val newRequest = chain.request()
      return chain.proceed(newRequest)
    }

    return response
  }
}
