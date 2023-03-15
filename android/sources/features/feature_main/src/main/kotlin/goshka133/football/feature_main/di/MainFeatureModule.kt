package goshka133.football.feature_main.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import goshka133.football.core_elmslie.StoreFactory
import goshka133.football.core_elmslie.StoreFactoryKey
import goshka133.football.domain_main.MainFeatureApi
import goshka133.football.feature_main.feature_api.MainFeatureApiImpl
import goshka133.football.feature_main.screens.main.presentation.MainStoreFactory

@Module
abstract class MainFeatureModule {

  @Binds internal abstract fun MainFeatureApiImpl.bindMainFeatureApi(): MainFeatureApi

  @Binds
  @IntoMap
  @StoreFactoryKey(MainStoreFactory::class)
  internal abstract fun MainStoreFactory.bindMainStoreFactory(): StoreFactory
}
