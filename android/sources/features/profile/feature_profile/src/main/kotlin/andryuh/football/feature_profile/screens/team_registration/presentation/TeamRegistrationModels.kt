package andryuh.football.feature_profile.screens.team_registration.presentation

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import andryuh.football.domain_profile.dto.Profile
import andryuh.football.ui_kit.text_field.toTextFieldValue

internal data class TeamRegistrationState(
  val profile: Profile,
  val photoUri: Uri? = null,
  val teamNameTextField: TextFieldValue = TextFieldValue(),
  val teamInfoTextField: TextFieldValue = TextFieldValue(),
  val captainNameTextField: TextFieldValue = profile.fullName.toTextFieldValue(),
  val isLoading: Boolean = false,
)

internal sealed interface TeamRegistrationEvent {

  sealed interface Ui : TeamRegistrationEvent {
    object System {
      object Start : Ui
    }

    object Click {

      object Back : Ui
      object Avatar : Ui

      object Continue : Ui

      object PhotoPickerSheetContinue : Ui
      object PhotoPickerSheetClose : Ui
    }

    object Action {

      data class OnImageReceived(val uri: Uri?) : Ui

      data class OnTeamNameTextFieldChange(val value: TextFieldValue) : Ui
      data class OnTeamInfoTextFieldChange(val value: TextFieldValue) : Ui
      data class OnCaptainNameTextFieldChange(val value: TextFieldValue) : Ui
    }
  }

  sealed interface Internal : TeamRegistrationEvent {}
}

internal sealed interface TeamRegistrationCommand {
  // your code
}

internal sealed interface TeamRegistrationEffect {

  object Close : TeamRegistrationEffect
  object OpenPhotoPicker : TeamRegistrationEffect

  object ShowBottomPhotoPickerSheet : TeamRegistrationEffect
  object HideBottomPhotoPickerSheet : TeamRegistrationEffect
}
