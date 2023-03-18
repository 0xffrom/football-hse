package goshka133.football.feature_search.di

import dagger.Module
import goshka133.football.domain_search.SearchFeatureApi
import goshka133.football.feature_search.feature_api.SearchFeatureApiImpl

@Module
abstract class SearchModule {

  internal abstract fun SearchFeatureApiImpl.bindSearchFeatureApi(): SearchFeatureApi
}
