package andryuh.football.feature_profile.screens.profile_application.presentation

import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationCommand as Command
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationEffect as Effect
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationEvent as Event
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationEvent.Internal
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationEvent.Ui
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationState as State
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object ProfileApplicationReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> Unit
      is Ui.Click.Back -> {
        effects { +Effect.Close }
      }
      is Ui.Click.Chat -> {
        effects { +Effect.OpenChat(state.playerApplication.id) }
      }
    }
  }

  override fun Result.internal(event: Internal) = Unit
}
