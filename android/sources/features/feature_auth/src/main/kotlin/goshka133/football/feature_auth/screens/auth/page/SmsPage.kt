package goshka133.football.feature_auth.screens.auth.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import goshka133.football.feature_auth.R
import goshka133.football.feature_auth.screens.auth.EventReceiver
import goshka133.football.feature_auth.screens.auth.presentation.AuthEvent
import goshka133.football.feature_auth.screens.auth.presentation.AuthState
import goshka133.football.ui_kit.text_field.FTextField
import goshka133.football.ui_kit.theme.CaptionRegular
import goshka133.football.ui_kit.theme.FootballColors
import goshka133.football.ui_kit.theme.Large

@Composable
internal fun SmsPage(
  page: AuthState.Page.SmsCode,
  eventReceiver: EventReceiver,
) {
  Column(
    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Image(
      modifier = Modifier.size(90.dp),
      painter = painterResource(id = R.drawable.img_auth_sms),
      contentDescription = null,
    )
    Spacer(modifier = Modifier.height(48.dp))
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = "Вход код",
      textAlign = TextAlign.Center,
      color = FootballColors.Text.Brand,
      style = Large,
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = "Мы отправили SMS с кодом на Ваш мобильный телефон",
      textAlign = TextAlign.Center,
      color = FootballColors.Text.Primary,
      style = CaptionRegular,
    )
    Spacer(modifier = Modifier.height(36.dp))
    FTextField(
      value = page.smsTextFieldValue,
      onValueChange = { textFieldValue ->
        eventReceiver.invoke(AuthEvent.Ui.Action.OnSmsTextFieldChange(textFieldValue))
      },
      keyboardOptions =
        remember {
          KeyboardOptions(
            keyboardType = KeyboardType.Number,
            autoCorrect = false,
            capitalization = KeyboardCapitalization.None,
            imeAction = ImeAction.Next,
          )
        },
      placeholder = "СМС КОД",
    )
  }
}