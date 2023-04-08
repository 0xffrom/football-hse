package goshka133.football.core_network.di

import goshka133.football.domain_auth.session.UserSessionProvider
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

internal class SessionHeaderInterceptor
@Inject
constructor(
  private val sessionProvider: UserSessionProvider,
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val sessionToken = sessionProvider.sessionFlow.value

    return chain.proceed(
      chain
        .request()
        .newBuilder()
        .apply {
          if (sessionToken != null) {
            addHeader("Authorization", "Bearer $sessionToken")
          }
        }
        .build()
    )
  }
}
