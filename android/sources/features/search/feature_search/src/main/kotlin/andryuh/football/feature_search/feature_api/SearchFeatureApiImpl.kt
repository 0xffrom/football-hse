package andryuh.football.feature_search.feature_api

import andryuh.football.domain_search.SearchFeatureApi
import andryuh.football.feature_search.screens.search.SearchScreen
import andryuh.football.feature_search.screens.search_player.SearchPlayerScreen
import com.github.terrakok.modo.Screen
import javax.inject.Inject

internal class SearchFeatureApiImpl @Inject constructor() : SearchFeatureApi {

  override fun getSearchTeamsScreen(): Screen = SearchScreen()

  override fun getSearchPlayersScreen(): Screen = SearchPlayerScreen()
}
