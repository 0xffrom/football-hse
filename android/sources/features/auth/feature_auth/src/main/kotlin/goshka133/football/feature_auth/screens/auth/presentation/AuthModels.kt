package goshka133.football.feature_auth.screens.auth.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import goshka133.football.domain_auth.session.UserSessionResponse
import goshka133.football.feature_auth.screens.auth.models.CodeDigit

internal const val PageNumbers = 2

@Immutable
internal data class AuthState(
  val isLoading: Boolean = false,
  val phoneNumberPage: Page.PhoneNumber = Page.PhoneNumber(),
  val smsCodePage: Page.SmsCode = Page.SmsCode(),
  val currentNumberPage: Int = 1,
  val previousNumberPage: Int = currentNumberPage,
  val isErrorPageValidationState: Boolean = false,
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
    val isError: Boolean

    fun validate(): Boolean

    @Immutable
    data class PhoneNumber(
      override val number: Int = 1,
      override val isError: Boolean = false,
      val numberTextFieldValue: TextFieldValue = TextFieldValue(),
    ) : Page {

      override fun validate(): Boolean {
        return numberTextFieldValue.text.length == 10
      }
    }

    @Immutable
    data class SmsCode(
      override val number: Int = 2,
      override val isError: Boolean = false,
      val smsTextFieldValue: TextFieldValue = TextFieldValue(),
    ) : Page {

      val digits = buildList {
        var previousSelected = false

        addAll(
          (0..3).map { index ->
            val value = smsTextFieldValue.text.getOrNull(index)?.toString() ?: ""

            val isSelected = value == "" && !previousSelected
            CodeDigit(
                number = value,
                isEmpty = value == "",
                isSelected = isSelected,
              )
              .also {
                if (isSelected) {
                  previousSelected = true
                }
              }
          }
        )
      }

      override fun validate(): Boolean {
        return smsTextFieldValue.text.length == 4
      }
    }
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

    object SendOtpSuccess : Internal
    data class SendOtpError(val error: Throwable) : Internal

    data class VerifyCodeSuccess(val session: UserSessionResponse) : Internal
    data class VerifyCodeError(val error: Throwable) : Internal
  }
}

internal sealed interface AuthCommand {

  data class SendOtp(val phoneNumber: String) : AuthCommand
  data class VerifyCode(val code: String) : AuthCommand
}

@Immutable
internal sealed interface AuthEffect {

  data class ShowError(val error: Throwable) : AuthEffect
  object Close : AuthEffect
  object OpenOriginationScreen : AuthEffect
  object OpenMainScreen : AuthEffect
}
