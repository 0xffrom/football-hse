package andryuh.football.feature_auth.screens.auth.presentation

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import andryuh.football.domain_auth.dto.UserSessionResponse
import andryuh.football.feature_auth.screens.auth.presentation.AuthCommand as Command
import andryuh.football.feature_auth.screens.auth.presentation.AuthEffect as Effect
import andryuh.football.feature_auth.screens.auth.presentation.AuthEvent as Event
import andryuh.football.feature_auth.screens.auth.presentation.AuthEvent.Internal
import andryuh.football.feature_auth.screens.auth.presentation.AuthEvent.Ui
import andryuh.football.feature_auth.screens.auth.presentation.AuthState as State
import andryuh.football.feature_auth.screens.auth.presentation.AuthState.Page
import andryuh.football.ui_kit.error.SomethingWentWrongException
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object AuthReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> Unit
      is Ui.Click.Continue -> {
        val page = state.currentPage
        val isPageValid = page.validate()
        state {
          copy(
            isErrorPageValidationState = !isPageValid,
          )
        }
        if (!isPageValid) {
          effects { +Effect.ShowError(IllegalStateException("Необходимо заполнить данные")) }
        } else {
          state {
            copy(
              isLoading = true,
            )
          }
          when (page) {
            is Page.PhoneNumber -> {
              commands { +Command.SendOtp(page.numberTextFieldValue.text) }
            }
            is Page.SmsCode -> {
              commands { +Command.VerifyCode(page.smsTextFieldValue.text) }
            }
          }
        }
      }
      is Ui.Click.Back -> {
        if (state.canGoBack) {
          state {
            copy(
              currentNumberPage = currentNumberPage - 1,
              previousNumberPage = state.currentNumberPage,
            )
          }
        } else {
          effects { +Effect.Close }
        }
      }
      is Ui.Action.OnPhoneNumberTextFieldChange -> {
        if (state.isLoading) return
        state {
          copy(
            phoneNumberPage =
              state.phoneNumberPage.copy(
                numberTextFieldValue =
                  event.textFieldValue.copy(
                    event.textFieldValue.text.filter(Char::isDigit).take(10)
                  ),
              ),
            isErrorPageValidationState = false,
          )
        }
      }
      is Ui.Action.OnSmsTextFieldChange -> {
        if (state.isLoading) return

        val formattedText = event.textFieldValue.text.filter(Char::isDigit).take(4)

        if (formattedText != state.smsCodePage.smsTextFieldValue.text) {
          state {
            copy(
              smsCodePage =
                state.smsCodePage.copy(
                  smsTextFieldValue =
                    TextFieldValue(
                      text = formattedText,
                      selection = TextRange(formattedText.length),
                    ),
                ),
              isErrorPageValidationState = false,
            )
          }

          if (formattedText.length == 4) {
            state { copy(isLoading = true) }
            commands { +Command.VerifyCode(formattedText) }
          }
        }
      }
    }
  }

  override fun Result.internal(event: Internal) {
    when (event) {
      is Internal.SendOtpSuccess -> {
        if (state.currentPage is Page.PhoneNumber) {
          state {
            copy(
              isLoading = false,
              currentNumberPage = state.currentNumberPage + 1,
            )
          }
        }
      }
      is Internal.SendOtpError -> {
        state {
          copy(
            isLoading = false,
          )
        }
        effects { +Effect.ShowError(SomethingWentWrongException()) }
      }
      is Internal.VerifyCodeSuccess -> {
        state {
          copy(
            isLoading = false,
          )
        }
        when (event.session) {
          is UserSessionResponse.Data -> {
            if (event.session.shouldBeOnboarded) {
              effects { +Effect.OpenOriginationScreen }
            } else {
              effects { +Effect.OpenMainScreen }
            }
          }
          is UserSessionResponse.Error.IncorrectCode -> {
            // TODO: shake
            effects { +Effect.ShowError(IllegalStateException("Некорректный код")) }
          }
        }
      }
      is Internal.VerifyCodeError -> {
        state {
          copy(
            isLoading = false,
          )
        }
        effects { +Effect.ShowError(SomethingWentWrongException()) }
      }
    }
  }
}
