package andryuh.football.domain_search

import com.github.terrakok.modo.Screen

interface SearchFeatureApi {

  fun getSearchTeamsScreen(): Screen

  fun getSearchPlayersScreen(): Screen
}
