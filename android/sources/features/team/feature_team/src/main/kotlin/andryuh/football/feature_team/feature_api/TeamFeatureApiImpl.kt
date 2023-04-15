package andryuh.football.feature_team.feature_api

import com.github.terrakok.modo.Screen
import andryuh.football.domain_team.TeamApplication
import andryuh.football.domain_team.TeamFeatureApi
import andryuh.football.feature_team.screens.team_details.TeamApplicationDetailsScreen
import javax.inject.Inject

internal class TeamFeatureApiImpl @Inject constructor() : TeamFeatureApi {

  override fun getApplicationDetailsScreen(application: TeamApplication): Screen {
    return TeamApplicationDetailsScreen(application)
  }
}
