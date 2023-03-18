package goshka133.football.feature_main.feature_api

import com.github.terrakok.modo.Screen
import goshka133.football.domain_main.MainFeatureApi
import goshka133.football.feature_main.screens.main.MainScreen
import javax.inject.Inject

internal class MainFeatureApiImpl @Inject constructor() : MainFeatureApi {

  override fun getScreen(): Screen = MainScreen()
}
