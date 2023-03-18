package goshka133.football.feature_main.screens.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.github.terrakok.modo.stack.back
import goshka133.football.core_di.rememberDependencies
import goshka133.football.core_elmslie.rememberEventReceiver
import goshka133.football.core_elmslie.rememberStore
import goshka133.football.core_navigation.LocalRouter
import goshka133.football.feature_main.di.MainFeatureDependencies
import goshka133.football.feature_main.screens.main.models.BottomBarTab
import goshka133.football.feature_main.screens.main.models.BottomBarTabType
import goshka133.football.feature_main.screens.main.presentation.MainEffect
import goshka133.football.feature_main.screens.main.presentation.MainEvent
import goshka133.football.feature_main.screens.main.presentation.MainStoreFactory
import goshka133.football.ui_kit.BaseScreen
import goshka133.football.ui_kit.snack_bar.LocalSnackBarHostState
import goshka133.football.ui_kit.theme.FootballColors
import kotlinx.parcelize.Parcelize
import vivid.money.elmslie.coroutines.states

@Parcelize
internal class MainScreen : BaseScreen() {

  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = MainStoreFactory::class.java,
        storeProvider = { factory, _ -> factory.create() },
      )

    val router = LocalRouter.current
    val snackbarHostState = LocalSnackBarHostState.current

    LaunchedEffect(Unit) {
      store.effects().collect { effect ->
        when (effect) {
          is MainEffect.Close -> router.back()
          is MainEffect.ShowError -> {
            effect.error.message?.let { snackbarHostState.showSnackbar(it) }
          }
        }
      }
    }

    val eventReceiver = store.rememberEventReceiver()

    BackHandler { eventReceiver.invoke(MainEvent.Ui.Click.Back) }

    val state by store.states.collectAsState(store.currentState)
    Scaffold(
      bottomBar = {
        BottomNavigation(
          modifier = Modifier.navigationBarsPadding(),
          elevation = 4.dp,
          backgroundColor = FootballColors.Background,
        ) {
          state.tabs.fastForEach { type ->
            BottomBarTab(
              type = type,
              selectedType = state.selectedTab,
              onClick = { bottomBarType ->
                eventReceiver.invoke(MainEvent.Ui.Click.BottomBarTab(bottomBarType))
              }
            )
          }
        }
      }
    ) { paddingValues: PaddingValues ->
      val dependencies: MainFeatureDependencies = rememberDependencies()
      val searchScreen = remember { dependencies.searchFeatureApi.getScreen() }
      val chatScreen = remember { dependencies.chatFeatureApi.getScreen() }
      val profileScreen = remember { dependencies.profileFeatureApi.getScreen() }

      Box(modifier = Modifier.padding(paddingValues)) {
        when (state.selectedTab) {
          BottomBarTabType.Search -> searchScreen.Content()
          BottomBarTabType.Chat -> chatScreen.Content()
          BottomBarTabType.Profile -> profileScreen.Content()
        }
      }
    }
  }
}
