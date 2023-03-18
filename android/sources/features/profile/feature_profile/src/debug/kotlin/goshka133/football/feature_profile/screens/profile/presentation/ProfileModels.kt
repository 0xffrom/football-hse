package goshka133.football.feature_profile.screens.profile.presentation

internal data class ProfileState(
  val isRefreshing: Boolean = false,
)

internal sealed interface ProfileEvent {

  sealed interface Ui : ProfileEvent {
    object System {
      object Start : Ui
    }

    object Click {
      // your code
    }

    object Action {
      // your code
    }
  }

  sealed interface Internal : ProfileEvent {
    // your code
  }
}

internal sealed interface ProfileCommand {
  // your code
}

internal sealed interface ProfileEffect {
  // your code
}
