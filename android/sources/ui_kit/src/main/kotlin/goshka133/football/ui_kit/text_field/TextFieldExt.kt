package goshka133.football.ui_kit.text_field

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

fun Any?.toTextFieldValue(): TextFieldValue {
  val str = this?.toString() ?: ""

  return TextFieldValue(
    text = str,
    selection = TextRange(str.length),
  )
}
