package andryuh.football.domain_profile

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
object ProfileDomainModule {

  @Provides
  @Singleton
  fun provideProfileApi(retrofit: Retrofit): ProfileApi {
    return retrofit.create(ProfileApi::class.java)
  }
}
