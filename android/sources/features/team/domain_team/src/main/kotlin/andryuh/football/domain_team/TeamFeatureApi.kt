package andryuh.football.domain_team

import andryuh.football.domain_team.dto.Team
import andryuh.football.domain_team.dto.TeamApplication
import com.github.terrakok.modo.Screen

interface TeamFeatureApi {

  fun getTeamApplicationDetailsScreen(application: TeamApplication): Screen

  fun getTeamDetails(team: Team): Screen
}
