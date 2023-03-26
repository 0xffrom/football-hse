package goshka133.football.feature_team.feature_api

import com.github.terrakok.modo.Screen
import goshka133.football.domain_team.TeamApplication
import goshka133.football.domain_team.TeamFeatureApi
import goshka133.football.feature_team.screens.team_details.TeamApplicationDetailsScreen
import javax.inject.Inject

internal class TeamFeatureApiImpl @Inject constructor() : TeamFeatureApi {

  override fun getApplicationDetailsScreen(application: TeamApplication): Screen {
    return TeamApplicationDetailsScreen(application)
  }
}
