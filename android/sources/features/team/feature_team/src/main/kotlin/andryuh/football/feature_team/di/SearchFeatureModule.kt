package andryuh.football.feature_team.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.core_elmslie.StoreFactoryKey
import andryuh.football.domain_team.TeamFeatureApi
import andryuh.football.feature_team.feature_api.TeamFeatureApiImpl
import andryuh.football.feature_team.screens.team_details.presentation.TeamApplicationDetailsStoreFactory

@Module
abstract class TeamFeatureModule {

  @Binds internal abstract fun TeamFeatureApiImpl.bindTeamFeatureApi(): TeamFeatureApi

  @Binds
  @IntoMap
  @StoreFactoryKey(TeamApplicationDetailsStoreFactory::class)
  internal abstract fun TeamApplicationDetailsStoreFactory.bindTeamApplicationDetailsStoreFactory(): StoreFactory
}
