package andryuh.football.feature_team.feature_api

import andryuh.football.domain_team.TeamFeatureApi
import andryuh.football.domain_team.dto.Team
import andryuh.football.domain_team.dto.TeamApplication
import andryuh.football.feature_team.screens.team_application_details.TeamApplicationDetailsScreen
import andryuh.football.feature_team.screens.team_details.TeamDetailsScreen
import com.github.terrakok.modo.Screen
import javax.inject.Inject

internal class TeamFeatureApiImpl @Inject constructor() : TeamFeatureApi {

  override fun getTeamApplicationDetailsScreen(application: TeamApplication): Screen {
    return TeamApplicationDetailsScreen(application)
  }

  override fun getTeamDetails(team: Team): Screen {
    return TeamDetailsScreen(team)
  }
}
