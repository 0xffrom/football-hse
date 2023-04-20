package andryuh.football.feature_profile.screens.profile_application.presentation

import andryuh.football.domain_profile.dto.PlayerApplication

internal data class ProfileApplicationState(
  val playerApplication: PlayerApplication,
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

  sealed interface Internal : ProfileApplicationEvent
}

internal sealed interface ProfileApplicationCommand

internal sealed interface ProfileApplicationEffect {

  object Close : ProfileApplicationEffect
  data class OpenChat(val playerId: String) : ProfileApplicationEffect
}
