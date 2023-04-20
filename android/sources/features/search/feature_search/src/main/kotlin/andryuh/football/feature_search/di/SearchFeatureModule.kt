package andryuh.football.feature_search.di

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.core_elmslie.StoreFactoryKey
import andryuh.football.domain_search.SearchApi
import andryuh.football.domain_search.SearchFeatureApi
import andryuh.football.feature_search.feature_api.SearchFeatureApiImpl
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersStoreFactory
import andryuh.football.feature_search.screens.search.presentation.SearchStoreFactory
import andryuh.football.feature_search.screens.search_player.presentation.SearchPlayerStoreFactory
import andryuh.football.feature_search.screens.search_player_application.presentation.SearchPlayerApplicationStoreFactory
import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsStoreFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import retrofit2.Retrofit

@Module(includes = [SearchFeatureProvidersModule::class])
abstract class SearchFeatureModule {

  @Binds internal abstract fun SearchFeatureApiImpl.bindSearchFeatureApi(): SearchFeatureApi

  @Binds
  @IntoMap
  @StoreFactoryKey(SearchStoreFactory::class)
  internal abstract fun SearchStoreFactory.bindSearchStoreFactory(): StoreFactory

  @Binds
  @IntoMap
  @StoreFactoryKey(SearchTeamDetailsStoreFactory::class)
  internal abstract fun SearchTeamDetailsStoreFactory.bindSearchTeamDetailsStoreFactory():
    StoreFactory

  @Binds
  @IntoMap
  @StoreFactoryKey(SearchPlayerStoreFactory::class)
  internal abstract fun SearchPlayerStoreFactory.bindSearchPlayerStoreFactory(): StoreFactory

  @Binds
  @IntoMap
  @StoreFactoryKey(SearchFiltersStoreFactory::class)
  internal abstract fun SearchFiltersStoreFactory.bindSearchFiltersStoreFactory(): StoreFactory

  @Binds
  @IntoMap
  @StoreFactoryKey(SearchPlayerApplicationStoreFactory::class)
  internal abstract fun SearchPlayerApplicationStoreFactory
    .bindSearchPlayerApplicationStoreFactory(): StoreFactory
}

@Module
object SearchFeatureProvidersModule {

  @Provides
  @Singleton
  fun provideSearchApi(retrofit: Retrofit): SearchApi {
    return retrofit.create(SearchApi::class.java)
  }
}
