package goshka133.football.feature_profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import goshka133.football.ui_kit.text_field.FTextField
import goshka133.football.ui_kit.theme.FootballColors

@Composable
internal fun FormField(
  textFieldValue: TextFieldValue,
  onValueChange: (value: TextFieldValue) -> Unit,
  title: String,
  placeholder: String,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(4.dp),
  ) {
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = remember(title) { title.uppercase() },
      textAlign = TextAlign.Start,
      color = FootballColors.Text.Secondary,
      style =
        TextStyle(
          fontWeight = FontWeight.W500,
          fontSize = 12.sp,
          lineHeight = 16.sp,
          letterSpacing = 0.5.sp,
        ),
    )
    FTextField(
      modifier = Modifier.fillMaxWidth(),
      value = textFieldValue,
      onValueChange = onValueChange,
      placeholder = placeholder,
    )
  }
}
