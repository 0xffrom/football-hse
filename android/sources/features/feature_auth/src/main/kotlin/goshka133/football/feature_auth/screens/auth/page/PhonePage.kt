package goshka133.football.feature_auth.screens.auth.page

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import goshka133.football.feature_auth.R
import goshka133.football.feature_auth.screens.auth.EventReceiver
import goshka133.football.feature_auth.screens.auth.presentation.AuthEvent.Ui.Action
import goshka133.football.feature_auth.screens.auth.presentation.AuthState
import goshka133.football.ui_kit.text_field.FTextField
import goshka133.football.ui_kit.theme.CaptionMRegular
import goshka133.football.ui_kit.theme.FootballColors
import goshka133.football.ui_kit.theme.Large

@Composable
internal fun PhonePage(
  state: AuthState,
  page: AuthState.Page.PhoneNumber,
  eventReceiver: EventReceiver,
  contentPadding: PaddingValues,
) {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = contentPadding,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    item {
      Image(
        modifier = Modifier.size(90.dp),
        painter = painterResource(id = R.drawable.img_auth_phone),
        contentDescription = null,
      )
      Spacer(modifier = Modifier.height(48.dp))
    }
    item {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Вход по номеру телефона",
        textAlign = TextAlign.Center,
        color = FootballColors.Text.Brand,
        style = Large,
      )
      Spacer(modifier = Modifier.height(16.dp))
    }
    item {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Введите номер вашего мобильного телефона для авторизации",
        textAlign = TextAlign.Center,
        color = FootballColors.Text.Primary,
        style = CaptionMRegular,
      )
      Spacer(modifier = Modifier.height(36.dp))
    }
    item {
      val focusRequester = remember { FocusRequester() }

      LaunchedEffect(Unit) {
        focusRequester.requestFocus()
      }

      FTextField(
        modifier = Modifier.focusRequester(focusRequester),
        value = page.numberTextFieldValue,
        onValueChange = { textFieldValue ->
          eventReceiver.invoke(Action.OnPhoneNumberTextFieldChange(textFieldValue))
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
        isError = state.isErrorPageValidationState,
        placeholder = "Номер телефона",
        visualTransformation =
          remember {
            object : VisualTransformation {
              override fun filter(text: AnnotatedString): TransformedText {
                return TransformedText(
                  text =
                    AnnotatedString(
                      text = "+7" + text.text.phoneFormat(),
                    ),
                  offsetMapping =
                    object : OffsetMapping {
                      override fun originalToTransformed(offset: Int): Int {
                        return when {
                          offset == 0 -> offset + 2
                          offset < 4 -> offset + 3
                          offset < 7 -> offset + 4
                          offset < 9 -> offset + 5
                          offset < 11 -> offset + 6
                          else -> offset
                        }
                      }

                      override fun transformedToOriginal(offset: Int): Int {
                        return when {
                          offset < 3 -> 0
                          offset < 8 -> offset - 3
                          offset < 12 -> offset - 4
                          offset < 14 -> offset - 5
                          offset < 16 -> offset - 6
                          else -> offset
                        }
                      }
                    },
                )
              }
            }
          },
      )
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}

/** Format from '9882126936' to '988-212-69-36' */
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
internal fun String.phoneFormat(): String {
  fun takePreviousParts(
    str: String,
    take: Int,
  ): String {
    return "${str.take(take).phoneFormat()}-${str.substring(take)}"
  }
  return when (this.length) {
    0 -> this
    1,
    2,
    3 -> "-$this"
    4,
    5,
    6 -> takePreviousParts(this, 3)
    7,
    8 -> takePreviousParts(this, 6)
    else -> takePreviousParts(take(10), 8)
  }
}
