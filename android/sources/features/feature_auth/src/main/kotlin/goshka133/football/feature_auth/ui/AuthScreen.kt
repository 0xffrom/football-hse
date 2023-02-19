package goshka133.football.feature_auth.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import kotlinx.parcelize.Parcelize

@Parcelize
internal class AuthScreen(override val screenKey: ScreenKey = generateScreenKey()) : Screen {

  @Composable
  override fun Content() {
    Text(text = "Hello world from auth module", modifier = Modifier.fillMaxSize())
  }
}
