package goshka133.football

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.stack.StackNavModel
import com.github.terrakok.modo.stack.StackScreen
import goshka133.football.core_di.getComponent
import goshka133.football.di.MainDependencies
import goshka133.football.ui_kit.FootballTheme
import kotlinx.parcelize.Parcelize

class MainActivity : ComponentActivity() {
  private var rootScreen: StackScreen? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val dependencies: MainDependencies = getComponent(this)

    rootScreen =
      Modo.init(savedInstanceState, rootScreen) {
        RootStackView(rootScreen = dependencies.authFeatureApi.getScreen())
      }

    setContent { FootballTheme { rootScreen?.Content() } }
  }
}

@Parcelize
class RootStackView(private val stackNavModel: StackNavModel) : StackScreen(stackNavModel) {

  constructor(rootScreen: Screen) : this(StackNavModel(rootScreen))

  @Composable
  override fun Content() {
    TopScreenContent { screen.Content() }
  }
}
