package andryuh.football

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import andryuh.football.core_di.getComponent
import andryuh.football.core_navigation.LocalRouter
import andryuh.football.di.RootDependencies
import andryuh.football.ui_kit.snack_bar.LocalSnackBarHostState
import andryuh.football.ui_kit.snack_bar.rememberSnackBarHostState
import andryuh.football.ui_kit.theme.FootballTheme
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.animation.ScreenTransition
import com.github.terrakok.modo.stack.StackNavModel
import com.github.terrakok.modo.stack.StackScreen
import kotlinx.parcelize.Parcelize

class MainActivity : ComponentActivity() {
  private var rootScreen: StackScreen? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)

    val dependencies: RootDependencies = getComponent(this)

    val rootStackView =
      RootStackView(rootScreen = dependencies.authFeatureApi.getScreen()) { finish() }

    rootScreen = Modo.init(savedInstanceState, rootScreen) { rootStackView }
    dependencies.routerHolder.setRouter(rootScreen!!)

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
class RootStackView(private val stackNavModel: StackNavModel, private val onFinish: () -> Unit) :
  StackScreen(stackNavModel) {

  constructor(rootScreen: Screen, onFinish: () -> Unit) : this(StackNavModel(rootScreen), onFinish)

  @OptIn(ExperimentalAnimationApi::class)
  @Composable
  override fun Content() {
    LaunchedEffect(stackNavModel.navigationState.stack.isEmpty()) {
      if (stackNavModel.navigationState.stack.isEmpty()) {
        onFinish.invoke()
      }
    }
    TopScreenContent { ScreenTransition() }
  }
}
