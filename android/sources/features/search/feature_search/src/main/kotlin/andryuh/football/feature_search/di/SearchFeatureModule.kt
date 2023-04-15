package andryuh.football.feature_search.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.core_elmslie.StoreFactoryKey
import andryuh.football.domain_search.SearchFeatureApi
import andryuh.football.feature_search.feature_api.SearchFeatureApiImpl
import andryuh.football.feature_search.screens.search.presentation.SearchStoreFactory

@Module
abstract class SearchFeatureModule {

  @Binds internal abstract fun SearchFeatureApiImpl.bindSearchFeatureApi(): SearchFeatureApi

  @Binds
  @IntoMap
  @StoreFactoryKey(SearchStoreFactory::class)
  internal abstract fun SearchStoreFactory.bindSearchStoreFactory(): StoreFactory
}
