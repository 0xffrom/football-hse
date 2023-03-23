package goshka133.football.feature_profile.screens.team_registration.presentation

internal data class TeamRegistrationState(
  val profileFullName: String,
)

internal sealed interface TeamRegistrationEvent {

  sealed interface Ui : TeamRegistrationEvent {
    object System {
      object Start : Ui
    }

    object Click {

      object Back: Ui
    }

    object Action {
      // your code
    }
  }

  sealed interface Internal : TeamRegistrationEvent {
    // your code
  }
}

internal sealed interface TeamRegistrationCommand {
  // your code
}

internal sealed interface TeamRegistrationEffect {

  object Close: TeamRegistrationEffect
}
