package andryuh.football.feature_team.di

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.core_elmslie.StoreFactoryKey
import andryuh.football.domain_team.TeamApi
import andryuh.football.domain_team.TeamFeatureApi
import andryuh.football.feature_team.feature_api.TeamFeatureApiImpl
import andryuh.football.feature_team.screens.team_details.presentation.TeamApplicationDetailsStoreFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import retrofit2.Retrofit

@Module(includes = [TeamFeatureProvidersModule::class])
abstract class TeamFeatureModule {

  @Binds internal abstract fun TeamFeatureApiImpl.bindTeamFeatureApi(): TeamFeatureApi

  @Binds
  @IntoMap
  @StoreFactoryKey(TeamApplicationDetailsStoreFactory::class)
  internal abstract fun TeamApplicationDetailsStoreFactory.bindTeamApplicationDetailsStoreFactory():
    StoreFactory
}

@Module
object TeamFeatureProvidersModule {

  @Provides
  @Singleton
  fun provideTeamApi(retrofit: Retrofit): TeamApi {
    return retrofit.create(TeamApi::class.java)
  }
}
