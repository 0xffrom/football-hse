package andryuh.football.feature_auth.screens.auth.page.sms

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import andryuh.football.feature_auth.R
import andryuh.football.feature_auth.screens.auth.EventReceiver
import andryuh.football.feature_auth.screens.auth.models.CodeDigit
import andryuh.football.feature_auth.screens.auth.presentation.AuthEvent
import andryuh.football.feature_auth.screens.auth.presentation.AuthState
import andryuh.football.ui_kit.borders.SelectedBorderModifier
import andryuh.football.ui_kit.theme.*

@Composable
internal fun SmsPage(
  state: AuthState,
  page: AuthState.Page.SmsCode,
  eventReceiver: EventReceiver,
  contentPadding: PaddingValues,
) {
  LazyColumn(
    modifier = Modifier.fillMaxSize().padding(contentPadding),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    item {
      Image(
        modifier = Modifier.size(90.dp),
        painter = painterResource(id = R.drawable.img_auth_sms),
        contentDescription = null,
      )
      Spacer(modifier = Modifier.height(48.dp))
    }
    item {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Вход код",
        textAlign = TextAlign.Center,
        color = FootballColors.Text.Brand,
        style = Large,
      )
      Spacer(modifier = Modifier.height(16.dp))
    }
    item {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Мы отправили SMS с кодом на Ваш мобильный телефон",
        textAlign = TextAlign.Center,
        color = FootballColors.Text.Primary,
        style = CaptionMRegular,
      )
      Spacer(modifier = Modifier.height(36.dp))
    }
    item {
      val focusRequester = remember { FocusRequester() }

      LaunchedEffect(Unit) { focusRequester.requestFocus() }

      BasicTextField(
        modifier = Modifier.focusRequester(focusRequester),
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
            page.digits.fastForEach { digit ->
              Digit(
                codeDigit = digit,
                isErrorState = state.isErrorPageValidationState,
              )
            }
          }
        },
      )
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}

@Composable
private fun Digit(
  codeDigit: CodeDigit,
  isErrorState: Boolean,
) {
  val selectedModifier =
    remember(codeDigit.isSelected, isErrorState) {
      when {
        isErrorState && codeDigit.isSelected -> {
          Modifier.border(
            width = 1.dp,
            color = FootballColors.Accent.Red,
            shape = DefaultShapes.large,
          )
        }
        codeDigit.isSelected -> SelectedBorderModifier
        else -> Modifier
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
