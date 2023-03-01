package goshka133.football.feature_auth.screens.auth.presentation

import androidx.compose.ui.text.input.TextFieldValue
import goshka133.football.feature_auth.screens.auth.presentation.AuthCommand as Command
import goshka133.football.feature_auth.screens.auth.presentation.AuthEffect as Effect
import goshka133.football.feature_auth.screens.auth.presentation.AuthEvent as Event
import goshka133.football.feature_auth.screens.auth.presentation.AuthEvent.Internal
import goshka133.football.feature_auth.screens.auth.presentation.AuthEvent.Ui
import goshka133.football.feature_auth.screens.auth.presentation.AuthState as State
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object AuthReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> Unit
      is Ui.Click.Continue -> {
        if (state.canGoNext) {
          state {
            copy(
              currentNumberPage = state.currentNumberPage + 1,
              previousNumberPage = state.currentNumberPage,
            )
          }
        } else {
          effects { +Effect.OpenMoreInfo }
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
                numberTextFieldValue = event.textFieldValue,
              )
          )
        }
      }
      is Ui.Action.OnSmsTextFieldChange -> {
        if (state.isLoading) return

        val formattedText = event.textFieldValue.text.filter(Char::isDigit)

        if (formattedText != state.smsCodePage.smsTextFieldValue.text) {
          state {
            copy(
              smsCodePage =
                state.smsCodePage.copy(
                  smsTextFieldValue = TextFieldValue(formattedText),
                )
            )
          }

          if (state.smsCodePage.smsTextFieldValue.text.length == 4) {
            state { copy(isLoading = true) }
            effects { +Effect.OpenMoreInfo }
          }
        }
      }
    }
  }

  override fun Result.internal(event: Internal) = Unit
}
