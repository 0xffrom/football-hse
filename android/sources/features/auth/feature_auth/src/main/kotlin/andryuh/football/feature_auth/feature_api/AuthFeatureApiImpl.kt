package andryuh.football.feature_auth.feature_api

import com.github.terrakok.modo.Screen
import andryuh.football.core_auth.feature_api.RefreshSessionFeatureApi
import andryuh.football.domain_auth.AuthFeatureApi
import andryuh.football.feature_auth.screens.auth.AuthScreen
import javax.inject.Inject

internal class AuthFeatureApiImpl @Inject constructor() : AuthFeatureApi {

  override fun getScreen(): Screen = AuthScreen()
}

internal class RefreshSessionFeatureApiImpl @Inject constructor() : RefreshSessionFeatureApi {

  override fun getScreen(): Screen = AuthScreen()
}
