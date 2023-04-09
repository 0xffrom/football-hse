package goshka133.football.feature_auth.feature_api

import com.github.terrakok.modo.Screen
import goshka133.football.core_auth.feature_api.RefreshSessionFeatureApi
import goshka133.football.domain_auth.AuthFeatureApi
import goshka133.football.feature_auth.screens.auth.AuthScreen
import javax.inject.Inject

internal class AuthFeatureApiImpl @Inject constructor() : AuthFeatureApi {

  override fun getScreen(): Screen = AuthScreen()
}

internal class RefreshSessionFeatureApiImpl @Inject constructor() : RefreshSessionFeatureApi {

  override fun getScreen(): Screen = AuthScreen()
}
