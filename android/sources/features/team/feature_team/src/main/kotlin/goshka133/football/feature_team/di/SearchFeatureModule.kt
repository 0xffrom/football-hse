package goshka133.football.feature_team.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import goshka133.football.core_elmslie.StoreFactory
import goshka133.football.core_elmslie.StoreFactoryKey
import goshka133.football.domain_team.TeamFeatureApi
import goshka133.football.feature_team.feature_api.TeamFeatureApiImpl
import goshka133.football.feature_team.screens.team_details.presentation.TeamDetailsStoreFactory

@Module
abstract class TeamFeatureModule {

  @Binds internal abstract fun TeamFeatureApiImpl.bindTeamFeatureApi(): TeamFeatureApi

  @Binds
  @IntoMap
  @StoreFactoryKey(TeamDetailsStoreFactory::class)
  internal abstract fun TeamDetailsStoreFactory.bindTeamDetailsStoreFactory(): StoreFactory
}
