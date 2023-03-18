package goshka133.football.feature_search.feature_api

import com.github.terrakok.modo.Screen
import goshka133.football.domain_search.SearchFeatureApi
import goshka133.football.feature_search.screens.search.SearchScreen
import javax.inject.Inject

internal class SearchFeatureApiImpl @Inject constructor() : SearchFeatureApi {

  override fun getScreen(): Screen = SearchScreen()
}
