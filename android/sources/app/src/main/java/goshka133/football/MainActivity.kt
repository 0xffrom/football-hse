package goshka133.football

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.animation.ScreenTransition
import com.github.terrakok.modo.stack.StackNavModel
import com.github.terrakok.modo.stack.StackScreen
import goshka133.football.core_di.getComponent
import goshka133.football.core_navigation.LocalRouter
import goshka133.football.di.RootDependencies
import goshka133.football.ui_kit.snack_bar.LocalSnackBarHostState
import goshka133.football.ui_kit.snack_bar.rememberSnackBarHostState
import goshka133.football.ui_kit.theme.FootballTheme
import kotlinx.parcelize.Parcelize

class MainActivity : ComponentActivity() {
  private var rootScreen: StackScreen? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)

    val dependencies: RootDependencies = getComponent(this)

    // TODO: a temporary solution instead of 'rootScreen = dependencies.authFeatureApi.getScreen()'
    val rootStackView = RootStackView(rootScreen = dependencies.authFeatureApi.getScreen())

    rootScreen = Modo.init(savedInstanceState, rootScreen) { rootStackView }

    setContent {
      CompositionLocalProvider(
        LocalRouter provides rootScreen!!,
        LocalSnackBarHostState provides rememberSnackBarHostState(),
      ) {
        FootballTheme {
          Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
          ) {
            Box(modifier = Modifier.fillMaxSize()) { rootScreen?.Content() }
            SnackbarHost(
              modifier = Modifier.fillMaxWidth().statusBarsPadding(),
              hostState = LocalSnackBarHostState.current
            )
          }
        }
      }
    }
  }
}

@Parcelize
class RootStackView(private val stackNavModel: StackNavModel) : StackScreen(stackNavModel) {

  constructor(rootScreen: Screen) : this(StackNavModel(rootScreen))

  @OptIn(ExperimentalAnimationApi::class)
  @Composable
  override fun Content() {
    TopScreenContent { ScreenTransition() }
  }
}
