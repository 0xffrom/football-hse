package andryuh.football.feature_main.feature_api

import com.github.terrakok.modo.Screen
import andryuh.football.domain_main.MainFeatureApi
import andryuh.football.feature_main.screens.main.MainScreen
import javax.inject.Inject

internal class MainFeatureApiImpl @Inject constructor() : MainFeatureApi {

  override fun getScreen(): Screen = MainScreen()
}
