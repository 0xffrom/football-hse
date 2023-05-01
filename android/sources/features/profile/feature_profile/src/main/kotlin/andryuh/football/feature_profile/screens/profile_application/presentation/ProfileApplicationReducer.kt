package andryuh.football.feature_profile.screens.profile_application.presentation

import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationCommand as Command
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationEffect as Effect
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationEvent as Event
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationEvent.Internal
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationEvent.Ui
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationState as State
import andryuh.football.ui_kit.error.SomethingWentWrongException
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
        state { copy(isCreatingChatLoading = true) }
        commands { +Command.CreateChat(state.playerApplication.phoneNumber) }
      }
    }
  }

  override fun Result.internal(event: Internal) {
    when (event) {
      Internal.CreateChatSuccess -> {
        state { copy(isCreatingChatLoading = false) }
        effects { +Effect.OpenChat }
      }
      is Internal.CreateChatError -> {
        state { copy(isCreatingChatLoading = false) }
        effects { +Effect.ShowError(SomethingWentWrongException()) }
      }
    }
  }
}
