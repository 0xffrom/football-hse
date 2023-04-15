package andryuh.football.domain_auth

import dagger.Binds
import dagger.Module
import dagger.Provides
import andryuh.football.core_auth.session.UserSessionApi
import andryuh.football.core_auth.session.UserSessionProvider
import andryuh.football.core_auth.session.UserSessionUpdater
import andryuh.football.domain_auth.session.UserSessionStorage
import javax.inject.Singleton
import retrofit2.Retrofit

@Module(includes = [AuthDomainBindsModule::class])
object AuthDomainModule {

  @Provides
  @Singleton
  fun provideAuthApi(retrofit: Retrofit): AuthApi {
    return retrofit.create(AuthApi::class.java)
  }

  @Provides
  @Singleton
  fun provideUserSessionApi(retrofit: Retrofit): UserSessionApi {
    return retrofit.create(UserSessionApi::class.java)
  }
}

@Module
private abstract class AuthDomainBindsModule {

  @Binds abstract fun UserSessionStorage.bindsUserSessionProvider(): UserSessionProvider

  @Binds abstract fun UserSessionStorage.bindsUserSessionUpdater(): UserSessionUpdater
}
