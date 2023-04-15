package andryuh.football.feature_auth.screens.origination.presentation

import andryuh.football.feature_auth.screens.origination.presentation.OriginationCommand as Command
import andryuh.football.feature_auth.screens.origination.presentation.OriginationEffect as Effect
import andryuh.football.feature_auth.screens.origination.presentation.OriginationEvent as Event
import andryuh.football.feature_auth.screens.origination.presentation.OriginationEvent.Internal
import andryuh.football.feature_auth.screens.origination.presentation.OriginationEvent.Ui
import andryuh.football.feature_auth.screens.origination.presentation.OriginationState as State
import andryuh.football.feature_auth.screens.origination.utils.NameTextFieldValidator
import andryuh.football.ui_kit.error.SomethingWentWrongException
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object OriginationReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> Unit
      is Ui.Click.Back -> {
        effects { +Effect.Close }
      }
      is Ui.Click.RoleCard -> {
        state {
          copy(
            selectedRoleType = event.roleType,
          )
        }
      }
      is Ui.Click.Continue -> {
        if (state.isLoading) return

        val isTextFieldCorrect = NameTextFieldValidator.isCorrect(state.nameTextFieldValue.text)

        if (isTextFieldCorrect) {
          state {
            copy(
              isNameTextFieldError = false,
              isLoading = true,
            )
          }
          commands {
            +Command.UpdateProfile(
              fullName = state.nameTextFieldValue.text,
              role = state.selectedRoleType,
            )
          }
        } else {
          state {
            copy(
              isNameTextFieldError = true,
            )
          }
          effects { +Effect.ShowError(error = IllegalStateException("Некорректно введено имя")) }
        }
      }
      is Ui.Action.OnNameTextFieldValueChanged -> {
        state {
          copy(
            isNameTextFieldError = false,
            nameTextFieldValue = event.value,
          )
        }
      }
    }
  }

  override fun Result.internal(event: Internal) {
    when (event) {
      is Internal.UpdateProfileSuccess -> {
        state { copy(isLoading = false) }
        effects { +Effect.OpenMain }
      }
      is Internal.UpdateProfileError -> {
        state { copy(isLoading = false) }
        effects { +Effect.ShowError(SomethingWentWrongException()) }
      }
    }
  }
}
