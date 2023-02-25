package goshka133.football.ui_kit.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import goshka133.football.ui_kit.theme.BodySemibold
import goshka133.football.ui_kit.theme.DefaultShapes
import goshka133.football.ui_kit.theme.FootballColors
import androidx.compose.material.Button as MaterialButton

@Immutable
sealed interface ButtonStyle {

  val height: Dp

  class Standard private constructor(override val height: Dp) : ButtonStyle {

    constructor() : this(height = 60.dp)

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other !is Standard) return false

      if (height != other.height) return false

      return true
    }

    override fun hashCode(): Int {
      return height.hashCode()
    }
  }
}

@Composable
fun FButton(
  text: String,
  modifier: Modifier = Modifier,
  buttonStyle: ButtonStyle = ButtonStyle.Standard(),
  isLoading: Boolean = false,
  onClick: () -> Unit,
) {
  MaterialButton(
    modifier = modifier
      .fillMaxWidth()
      .height(buttonStyle.height),
    onClick = onClick,
    colors =
    ButtonDefaults.buttonColors(
      backgroundColor = FootballColors.Primary,
      contentColor = FootballColors.Text.White,
    ),
    shape = DefaultShapes.large,
    contentPadding =
    PaddingValues(
      horizontal = 24.dp,
      vertical = 16.dp,
    )
  ) {
    if (isLoading) {
      CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
        color = FootballColors.Icons.White,
        strokeWidth = 2.dp,
        strokeCap = StrokeCap.Square,
      )
    } else {
      Text(
        text = text,
        color = FootballColors.Text.White,
        style = BodySemibold,
      )
    }
  }
}

@Composable
@Preview
fun ButtonPreview_Text() {
  FButton(
    text = "Проверяем",
    onClick = {},
  )
}

@Composable
@Preview
fun ButtonPreview_Loading() {
  FButton(
    text = "Проверяем",
    isLoading = true,
    onClick = {},
  )
}
