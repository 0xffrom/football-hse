package goshka133.football.feature_auth.feature_api

import com.github.terrakok.modo.Screen
import goshka133.football.domain_auth.AuthFeatureApi
import goshka133.football.feature_auth.ui.AuthScreen
import javax.inject.Inject

internal class AuthFeatureApiImpl @Inject constructor() : AuthFeatureApi {

  override fun getScreen(): Screen = AuthScreen()
}
