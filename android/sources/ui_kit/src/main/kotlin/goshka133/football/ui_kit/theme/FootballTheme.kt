package goshka133.football.ui_kit.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun FootballTheme(content: @Composable () -> Unit) {
  CompositionLocalProvider(
    LocalFootballColors provides FootballColors,
  ) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
      systemUiController.setSystemBarsColor(
        color = Color.Transparent,
        darkIcons = true,
      )
    }

    MaterialTheme(
      colors =
        lightColors(
          primary = FootballColors.Primary,
          background = FootballColors.Background,
          error = FootballColors.Accent.Red,
        ),
      shapes = DefaultShapes,
      typography = FootballTypography,
    ) {
      content.invoke()
    }
  }
}

val DefaultShapes =
  Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp),
  )
val LocalFootballColors = staticCompositionLocalOf { FootballColors }
