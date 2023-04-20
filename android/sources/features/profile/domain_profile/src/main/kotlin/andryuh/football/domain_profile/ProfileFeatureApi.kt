package andryuh.football.domain_profile

import andryuh.football.domain_profile.dto.PlayerApplication
import com.github.terrakok.modo.Screen

interface ProfileFeatureApi {

  fun getProfileScreen(): Screen

  fun getProfileApplicationScreen(
    application: PlayerApplication,
    isChatSupport: Boolean = false,
  ): Screen
}
