package goshka133.football.ui_kit.text_field

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField as MaterialTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import goshka133.football.ui_kit.theme.BodyRegular
import goshka133.football.ui_kit.theme.DefaultShapes
import goshka133.football.ui_kit.theme.FootballColors

@Composable
fun FTextField(
  value: TextFieldValue,
  onValueChange: (TextFieldValue) -> Unit,
  modifier: Modifier = Modifier,
  placeholder: String? = null,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  isError: Boolean = false,
) {
  var isFocused by remember { mutableStateOf(false) }

  val borderModifier =
    remember(isError) {
      val color =
        if (isError) {
          FootballColors.Accent.Red
        } else {
          FootballColors.Primary
        }
      Modifier.border(width = 1.dp, color = color, shape = DefaultShapes.large)
    }

  MaterialTextField(
    modifier =
      modifier
        .heightIn(min = 54.dp)
        .onFocusChanged { focusState -> isFocused = focusState.isFocused }
        .then(
          if (isFocused) {
            borderModifier
          } else {
            Modifier
          }
        ),
    value = value,
    onValueChange = onValueChange,
    visualTransformation = visualTransformation,
    textStyle = BodyRegular,
    placeholder = placeholder?.let { placeholderText -> { Placeholder(placeholderText) } },
    colors =
      TextFieldDefaults.textFieldColors(
        textColor = FootballColors.Text.Primary,
        backgroundColor = FootballColors.Surface1,
        placeholderColor = FootballColors.Text.Tertiary,
        cursorColor = FootballColors.Text.Primary,
        errorCursorColor = FootballColors.Accent.Red,
        // Indicators ↴
        focusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
      ),
    shape = DefaultShapes.large,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
  )
}

@Composable
fun Placeholder(
  text: String,
) {
  Text(
    text = text,
    style = BodyRegular,
    color = FootballColors.Text.Tertiary,
  )
}

@Composable
@Preview
private fun TextFieldPreview() {
  val textFieldValue = remember { mutableStateOf(TextFieldValue()) }
  FTextField(
    modifier = Modifier.width(300.dp),
    value = textFieldValue.value,
    onValueChange = { textFieldValue.value = it },
    placeholder = "Номер телефона"
  )
}
