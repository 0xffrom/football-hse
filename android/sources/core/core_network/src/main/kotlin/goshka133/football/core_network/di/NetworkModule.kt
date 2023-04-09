package goshka133.football.core_network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import goshka133.football.core_auth.session.UserSessionProvider
import goshka133.football.core_auth.session.UserSessionUpdater
import goshka133.football.core_network.interceptors.SessionExpirationInterceptor
import goshka133.football.core_network.interceptors.SessionHeaderInterceptor
import javax.inject.Singleton
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private const val BaseServiceUrl = "http://hse-football.ru/api/"

@Module
object NetworkModule {

  private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    coerceInputValues = true
  }

  @OptIn(ExperimentalSerializationApi::class)
  @Provides
  @Singleton
  fun provideRetrofit(client: OkHttpClient): Retrofit {
    val contentType = "application/json".toMediaType()

    val converterFactory = json.asConverterFactory(contentType)

    return Retrofit.Builder()
      .baseUrl(BaseServiceUrl)
      .addConverterFactory(converterFactory)
      .client(client)
      .build()
  }

  @Provides
  @Singleton
  fun provideNetworkClient(interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient {
    val builder = OkHttpClient.Builder()

    interceptors.forEach { interceptor -> builder.addInterceptor(interceptor) }

    return builder.build()
  }

  @IntoSet
  @Provides
  fun provideLoggerInterceptor(): Interceptor {
    return HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
  }

  @IntoSet
  @Provides
  fun provideSessionHeaderInterceptor(userSessionProvider: UserSessionProvider): Interceptor {
    return SessionHeaderInterceptor(userSessionProvider)
  }

  @IntoSet
  @Provides
  fun provideSessionExpirationInterceptor(sessionUpdater: UserSessionUpdater): Interceptor {
    return SessionExpirationInterceptor(sessionUpdater)
  }
}
