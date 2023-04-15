package andryuh.football.feature_profile.feature_api

import com.github.terrakok.modo.Screen
import andryuh.football.domain_profile.ProfileFeatureApi
import andryuh.football.feature_profile.screens.profile.ProfileScreen
import javax.inject.Inject

internal class ProfileFeatureApiImpl @Inject constructor() : ProfileFeatureApi {

  override fun getScreen(): Screen = ProfileScreen()
}
