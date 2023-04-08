package goshka133.football.core_network.di

import goshka133.football.domain_auth.session.UserSessionUpdater
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

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
