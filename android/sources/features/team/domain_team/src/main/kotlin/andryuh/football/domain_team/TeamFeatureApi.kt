package andryuh.football.domain_team

import com.github.terrakok.modo.Screen

interface TeamFeatureApi {

  fun getApplicationDetailsScreen(application: TeamApplication): Screen
}
