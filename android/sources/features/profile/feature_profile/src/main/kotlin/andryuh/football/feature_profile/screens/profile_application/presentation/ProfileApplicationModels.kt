package andryuh.football.feature_profile.screens.profile_application.presentation

import andryuh.football.domain_profile.dto.PlayerApplication

internal data class ProfileApplicationState(
  val playerApplication: PlayerApplication,
  val isCreatingChatLoading: Boolean = false,
)

internal sealed interface ProfileApplicationEvent {

  sealed interface Ui : ProfileApplicationEvent {

    sealed interface System : Ui {

      object Start : System
    }

    sealed interface Click : Ui {

      object Back : Ui
      object Chat : Ui
    }
  }

  sealed interface Internal : ProfileApplicationEvent {

    object CreateChatSuccess : Internal
    data class CreateChatError(val error: Throwable) : Internal
  }
}

internal sealed interface ProfileApplicationCommand {

  data class CreateChat(val phoneNumber: String) : ProfileApplicationCommand
}

internal sealed interface ProfileApplicationEffect {

  object Close : ProfileApplicationEffect
  object OpenChat : ProfileApplicationEffect

  data class ShowError(val error: Throwable) : ProfileApplicationEffect
}
