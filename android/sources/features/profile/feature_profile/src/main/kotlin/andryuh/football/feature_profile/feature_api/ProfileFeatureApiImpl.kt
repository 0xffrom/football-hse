package andryuh.football.feature_profile.feature_api

import andryuh.football.domain_profile.ProfileFeatureApi
import andryuh.football.domain_profile.dto.PlayerApplication
import andryuh.football.feature_profile.screens.profile.ProfileScreen
import andryuh.football.feature_profile.screens.profile_application.ProfileApplicationScreen
import com.github.terrakok.modo.Screen
import javax.inject.Inject

internal class ProfileFeatureApiImpl @Inject constructor() : ProfileFeatureApi {

  override fun getProfileScreen(): Screen = ProfileScreen()

  override fun getProfileApplicationScreen(
    application: PlayerApplication,
    isChatSupport: Boolean,
  ): Screen = ProfileApplicationScreen(application, isChatSupport)
}
