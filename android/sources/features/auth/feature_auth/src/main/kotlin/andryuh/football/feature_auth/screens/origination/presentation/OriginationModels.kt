package andryuh.football.feature_auth.screens.origination.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import andryuh.football.domain_profile.dto.RoleType
import andryuh.football.feature_auth.screens.origination.models.RoleCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Immutable
internal data class OriginationState(
  val isLoading: Boolean = false,
  val roleCards: ImmutableList<RoleCard> = RoleCard.createList().toImmutableList(),
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

    object UpdateProfileSuccess : Internal
    data class UpdateProfileError(val error: Throwable) : Internal
  }
}

internal sealed interface OriginationCommand {

  data class UpdateProfile(val fullName: String, val role: RoleType) : OriginationCommand
}

@Immutable
internal sealed interface OriginationEffect {

  object OpenMain : OriginationEffect

  data class ShowError(val error: Throwable) : OriginationEffect
  object Close : OriginationEffect
}
