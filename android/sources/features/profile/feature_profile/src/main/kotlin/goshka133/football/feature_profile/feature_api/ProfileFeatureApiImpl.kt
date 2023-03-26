package goshka133.football.feature_profile.feature_api

import com.github.terrakok.modo.Screen
import goshka133.football.domain_profile.ProfileFeatureApi
import goshka133.football.feature_profile.screens.profile.ProfileScreen
import javax.inject.Inject

internal class ProfileFeatureApiImpl @Inject constructor() : ProfileFeatureApi {

  override fun getScreen(): Screen = ProfileScreen()
}
