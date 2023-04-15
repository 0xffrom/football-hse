package andryuh.football.feature_search.feature_api

import com.github.terrakok.modo.Screen
import andryuh.football.domain_search.SearchFeatureApi
import andryuh.football.feature_search.screens.search.SearchScreen
import javax.inject.Inject

internal class SearchFeatureApiImpl @Inject constructor() : SearchFeatureApi {

  override fun getScreen(): Screen = SearchScreen()
}
