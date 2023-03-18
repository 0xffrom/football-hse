package goshka133.football.feature_auth.screens.origination.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import goshka133.football.domain_auth.dto.RoleType
import goshka133.football.feature_auth.screens.origination.models.RoleCard

@Immutable
internal data class OriginationState(
  val isLoading: Boolean = false,
  // TODO: change to ImmutableList from kotlinX for the skipping recompositions
  val roleCards: List<RoleCard> = RoleCard.createList(),
  val selectedRoleType: RoleType = RoleType.Student,
  val nameTextFieldValue: TextFieldValue = TextFieldValue(),
  val isNameTextFieldError: Boolean = false,
)

internal sealed interface OriginationEvent {

  sealed interface Ui : OriginationEvent {
    object System {
      object Start : Ui
    }

    object Click {

      object Continue : Ui
      object Back : Ui

      data class RoleCard(val roleType: RoleType) : Ui
    }

    object Action {

      data class OnNameTextFieldValueChanged(val value: TextFieldValue) : Ui
    }
  }

  sealed interface Internal : OriginationEvent {
    // your code
  }
}

internal sealed interface OriginationCommand {
  // your code
}

@Immutable
internal sealed interface OriginationEffect {

  object OpenMain : OriginationEffect

  data class ShowError(val error: Throwable) : OriginationEffect
  object Close : OriginationEffect
}
