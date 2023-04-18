package andryuh.football.feature_search.di

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.core_elmslie.StoreFactoryKey
import andryuh.football.domain_search.SearchApi
import andryuh.football.domain_search.SearchFeatureApi
import andryuh.football.feature_search.feature_api.SearchFeatureApiImpl
import andryuh.football.feature_search.screens.search.presentation.SearchStoreFactory
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
}

@Module
object SearchFeatureProvidersModule {

  @Provides
  @Singleton
  fun provideSearchApi(retrofit: Retrofit): SearchApi {
    return retrofit.create(SearchApi::class.java)
  }
}
