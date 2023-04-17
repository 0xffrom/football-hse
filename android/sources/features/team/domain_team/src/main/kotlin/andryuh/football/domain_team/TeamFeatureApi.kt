package andryuh.football.domain_team

import andryuh.football.domain_team.dto.TeamApplication
import com.github.terrakok.modo.Screen

interface TeamFeatureApi {

  fun getApplicationDetailsScreen(application: TeamApplication): Screen
}
