package andryuh.football.ui_kit.text_field

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import andryuh.football.ui_kit.theme.BodyRegular
import andryuh.football.ui_kit.theme.DefaultShapes
import andryuh.football.ui_kit.theme.FootballColors
import kotlinx.coroutines.android.awaitFrame

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun FTextField(
  value: TextFieldValue,
  onValueChange: (TextFieldValue) -> Unit,
  modifier: Modifier = Modifier,
  placeholder: String? = null,
  minHeight: Dp = TextFieldDefaults.MinHeight,
  leadingIcon: @Composable (() -> Unit)? = null,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  isError: Boolean = false,
  singleLine: Boolean = false,
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

  val colors =
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
    )
  // If color is not provided via the text style, use content color as a default
  val textColor = BodyRegular.color.takeOrElse { colors.textColor(true).value }
  val mergedTextStyle = BodyRegular.merge(TextStyle(color = textColor))

  val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

  val bringIntoViewRequester = remember { BringIntoViewRequester() }

  if (isFocused) {

    LaunchedEffect(Unit) {
      awaitFrame()
      bringIntoViewRequester.bringIntoView()
    }
  }

  BasicTextField(
    value = value,
    modifier =
      modifier
        .onFocusChanged { focusState -> isFocused = focusState.isFocused }
        .then(
          if (isFocused) {
            borderModifier
          } else {
            Modifier
          }
        )
        .bringIntoViewRequester(bringIntoViewRequester)
        .background(colors.backgroundColor(true).value, DefaultShapes.large)
        .indicatorLine(true, isError, interactionSource, colors)
        .defaultMinSize(
          minWidth = TextFieldDefaults.MinWidth,
          minHeight = minHeight,
        ),
    onValueChange = onValueChange,
    enabled = true,
    readOnly = false,
    textStyle = mergedTextStyle,
    cursorBrush = SolidColor(colors.cursorColor(isError).value),
    visualTransformation = visualTransformation,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    interactionSource = interactionSource,
    singleLine = singleLine,
    maxLines = if (singleLine) 1 else Int.MAX_VALUE,
    minLines = 1,
    decorationBox =
      @Composable { innerTextField ->
        // places leading icon, text field with label and placeholder, trailing icon
        TextFieldDefaults.TextFieldDecorationBox(
          value = value.text,
          visualTransformation = visualTransformation,
          innerTextField = innerTextField,
          placeholder = placeholder?.let { placeholderText -> { Placeholder(placeholderText) } },
          label = null,
          leadingIcon = leadingIcon,
          trailingIcon = null,
          singleLine = singleLine,
          enabled = true,
          isError = isError,
          interactionSource = interactionSource,
          colors = colors
        )
      }
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
