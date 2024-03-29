package andryuh.football.feature_main.screens.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import andryuh.football.core_di.rememberDependencies
import andryuh.football.core_elmslie.rememberEventReceiver
import andryuh.football.core_elmslie.rememberStore
import andryuh.football.core_navigation.LocalRouter
import andryuh.football.feature_main.di.MainFeatureDependencies
import andryuh.football.feature_main.screens.main.models.BottomBarTab
import andryuh.football.feature_main.screens.main.models.BottomBarTabType
import andryuh.football.feature_main.screens.main.presentation.MainEffect
import andryuh.football.feature_main.screens.main.presentation.MainEvent
import andryuh.football.feature_main.screens.main.presentation.MainStoreFactory
import andryuh.football.ui_kit.BaseScreen
import andryuh.football.ui_kit.snack_bar.LocalSnackBarHostState
import andryuh.football.ui_kit.theme.FootballColors
import com.github.terrakok.modo.stack.back
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import vivid.money.elmslie.coroutines.states

@Parcelize
internal class MainScreen : BaseScreen() {

  @OptIn(ExperimentalAnimationApi::class)
  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = MainStoreFactory::class.java,
        storeProvider = { factory, _ -> factory.create() },
      )

    val router = LocalRouter.current
    val snackBarHostState = LocalSnackBarHostState.current

    LaunchedEffect(Unit) {
      store.effects().collect { effect ->
        when (effect) {
          is MainEffect.Close -> router.back()
          is MainEffect.ShowError -> {
            CoroutineScope(Dispatchers.Main).launch {
              effect.error.message?.let { snackBarHostState.showSnackbar(it) }
            }
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
          elevation = 0.dp,
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
      val searchScreen = remember { dependencies.searchFeatureApi.getSearchTeamsScreen() }
      val searchPlayerScreen = remember { dependencies.searchFeatureApi.getSearchPlayersScreen() }
      val chatScreen = remember { dependencies.chatFeatureApi.getScreen() }
      val profileScreen = remember { dependencies.profileFeatureApi.getProfileScreen() }

      AnimatedContent(
        modifier = Modifier.padding(paddingValues),
        targetState = state.selectedTab,
        transitionSpec = {
          val isLeftToRight = if (state.selectedTab > state.previousTab) 1 else -1

          slideIn(
              animationSpec = tween(600),
              initialOffset = { IntOffset(it.width * isLeftToRight, 0) },
            )
            .with(
              slideOut(
                animationSpec = tween(600),
                targetOffset = { IntOffset(-it.width * isLeftToRight, 0) },
              )
            )
            .using(
              SizeTransform(
                clip = true,
                sizeAnimationSpec = { _, _ -> tween(600, easing = EaseInOut) }
              )
            )
        },
        label = "",
      ) {
        when (it) {
          BottomBarTabType.SearchTeam -> searchScreen.Content()
          BottomBarTabType.SearchPlayer -> searchPlayerScreen.Content()
          BottomBarTabType.Chat -> chatScreen.Content()
          BottomBarTabType.Profile -> profileScreen.Content()
        }
      }
    }
  }
}
