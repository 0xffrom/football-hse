package goshka133.football.feature_auth.screens.auth.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue

internal const val PageNumbers = 2

@Immutable
internal data class AuthState(
  val isLoading: Boolean = false,
  val phoneNumberPage: Page.PhoneNumber = Page.PhoneNumber(),
  val smsCodePage: Page.SmsCode = Page.SmsCode(),
  val currentNumberPage: Int = 1,
  val previousNumberPage: Int = currentNumberPage,
) {

  val canGoBack = currentNumberPage > 1
  val canGoNext = currentNumberPage < PageNumbers

  val currentPage: Page
    get() = getPageByNumber(currentNumberPage)

  fun getPageByNumber(number: Int): Page {
    return when (number) {
      1 -> phoneNumberPage
      2 -> smsCodePage
      else -> error("Incorrect number")
    }
  }

  sealed interface Page {
    val number: Int

    @Immutable
    data class PhoneNumber(
      override val number: Int = 1,
      val numberTextFieldValue: TextFieldValue = TextFieldValue(),
    ) : Page

    @Immutable
    data class SmsCode(
      override val number: Int = 2,
      val smsTextFieldValue: TextFieldValue = TextFieldValue(),
    ) : Page
  }
}

internal sealed interface AuthEvent {

  sealed interface Ui : AuthEvent {
    object System {
      object Start : Ui
    }

    object Click {

      object Continue : Ui
      object Back : Ui
    }

    object Action {

      data class OnPhoneNumberTextFieldChange(val textFieldValue: TextFieldValue) : Ui
      data class OnSmsTextFieldChange(val textFieldValue: TextFieldValue) : Ui
    }
  }

  sealed interface Internal : AuthEvent {
    // your code
  }
}

internal sealed interface AuthCommand {
  // your code
}

@Immutable
internal sealed interface AuthEffect {

  object Close : AuthEffect
  object OpenMoreInfo : AuthEffect
}
