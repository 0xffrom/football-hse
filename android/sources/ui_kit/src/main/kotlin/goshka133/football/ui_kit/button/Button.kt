package goshka133.football.ui_kit.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button as MaterialButton
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import goshka133.football.ui_kit.theme.BodySemibold
import goshka133.football.ui_kit.theme.DefaultShapes
import goshka133.football.ui_kit.theme.FootballColors

@Immutable
sealed interface ButtonStyle {

  val height: Dp
  val backgroundColor: Color
  val contentColor: Color
  val borderColor: Color

  companion object {

    val Primary = Primary()
    val Secondary = Secondary()
  }

  class Primary
  private constructor(
    override val height: Dp,
    override val backgroundColor: Color,
    override val contentColor: Color,
    override val borderColor: Color
  ) : ButtonStyle {

    constructor() :
      this(
        height = 60.dp,
        backgroundColor = FootballColors.Primary,
        borderColor = FootballColors.Primary,
        contentColor = FootballColors.Text.White,
      )

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other !is Primary) return false

      if (height != other.height) return false
      if (backgroundColor != other.backgroundColor) return false
      if (contentColor != other.contentColor) return false
      if (borderColor != other.borderColor) return false

      return true
    }

    override fun hashCode(): Int {
      var result = height.hashCode()
      result = 31 * result + backgroundColor.hashCode()
      result = 31 * result + contentColor.hashCode()
      result = 31 * result + borderColor.hashCode()
      return result
    }

    override fun toString(): String {
      return "Primary(height=$height, backgroundColor=$backgroundColor, textColor=$contentColor, borderColor=$borderColor)"
    }
  }

  class Secondary
  private constructor(
    override val height: Dp,
    override val backgroundColor: Color,
    override val contentColor: Color,
    override val borderColor: Color
  ) : ButtonStyle {

    constructor() :
      this(
        height = 60.dp,
        backgroundColor = FootballColors.Text.White,
        borderColor = FootballColors.Primary,
        contentColor = FootballColors.Text.Primary,
      )

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other !is Secondary) return false

      if (height != other.height) return false
      if (backgroundColor != other.backgroundColor) return false
      if (contentColor != other.contentColor) return false
      if (borderColor != other.borderColor) return false

      return true
    }

    override fun hashCode(): Int {
      var result = height.hashCode()
      result = 31 * result + backgroundColor.hashCode()
      result = 31 * result + contentColor.hashCode()
      result = 31 * result + borderColor.hashCode()
      return result
    }

    override fun toString(): String {
      return "Secondary(height=$height, backgroundColor=$backgroundColor, textColor=$contentColor, borderColor=$borderColor)"
    }
  }
}

@Composable
fun FButton(
  text: String,
  modifier: Modifier = Modifier,
  buttonStyle: ButtonStyle = ButtonStyle.Primary(),
  isLoading: Boolean = false,
  onClick: () -> Unit,
) {
  MaterialButton(
    modifier = modifier.fillMaxWidth().height(buttonStyle.height),
    onClick = onClick,
    colors =
      ButtonDefaults.buttonColors(
        backgroundColor = buttonStyle.backgroundColor,
        contentColor = buttonStyle.contentColor,
      ),
    border = BorderStroke(2.dp, buttonStyle.borderColor),
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
        color = buttonStyle.contentColor,
        strokeWidth = 2.dp,
        strokeCap = StrokeCap.Square,
      )
    } else {
      Text(
        text = text,
        color = buttonStyle.contentColor,
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
