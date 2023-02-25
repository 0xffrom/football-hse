package goshka133.football.core_network.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// TODO
private const val BaseServiceUrl = "*empty*"

@Module
object NetworkModule {

  @Provides
  @Singleton
  fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BaseServiceUrl)
      .addConverterFactory(MoshiConverterFactory.create())
      .client(client)
      .build()
  }

  @Provides
  @Singleton
  fun provideNetworkClient(interceptors: Set<Interceptor>): OkHttpClient {
    val builder = OkHttpClient.Builder()

    interceptors.forEach { interceptor -> builder.addInterceptor(interceptor) }

    return builder.build()
  }

  @IntoSet
  @Provides
  @Singleton
  fun provideLoggerInterceptor(): Interceptor {
    return HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
  }
}
