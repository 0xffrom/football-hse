package goshka133.football.feature_auth.screens.auth.page

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.util.fastForEach
import goshka133.football.feature_auth.R
import goshka133.football.feature_auth.screens.auth.EventReceiver
import goshka133.football.feature_auth.screens.auth.models.CodeDigit
import goshka133.football.feature_auth.screens.auth.presentation.AuthEvent
import goshka133.football.feature_auth.screens.auth.presentation.AuthState
import goshka133.football.ui_kit.theme.*

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
    BasicTextField(
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
      decorationBox = {
        Row(
          horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        ) {
          page.digits.fastForEach { digit -> Digit(digit) }
        }
      },
    )
    Spacer(modifier = Modifier.height(20.dp))
  }
}

@Composable
private fun Digit(codeDigit: CodeDigit) {
  val selectedModifier =
    remember(codeDigit.isSelected) {
      if (codeDigit.isSelected) {
        Modifier.border(width = 1.dp, color = FootballColors.Primary, shape = DefaultShapes.large)
      } else {
        Modifier
      }
    }

  Text(
    text = codeDigit.number,
    modifier =
      Modifier.size(
          width = 56.dp,
          height = 60.dp,
        )
        .background(
          color = FootballColors.Surface1,
          shape = DefaultShapes.large,
        )
        .then(selectedModifier)
        .wrapContentHeight(),
    textAlign = TextAlign.Center,
    style = SmsCode,
    color = FootballColors.Primary,
  )
}
