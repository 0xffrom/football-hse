package andryuh.football.feature_main.screens.main.presentation

import andryuh.football.domain_main.BottomTabController
import andryuh.football.domain_profile.ProfileRepository
import andryuh.football.feature_main.screens.main.presentation.MainCommand as Command
import andryuh.football.feature_main.screens.main.presentation.MainEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor

internal class MainActor
@Inject
constructor(
  private val profileRepository: ProfileRepository,
  private val bottomBarTabController: BottomTabController,
) : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> {
    return when (command) {
      is Command.ObserveCaptain ->
        profileRepository
          .observeCaptain()
          .mapEvents(
            eventMapper = Internal::ObserveCaptainSuccess,
          )
      is Command.ChangeTab -> flow { emit(bottomBarTabController.change(command.tab)) }.mapEvents()
      is Command.ObserveTab ->
        bottomBarTabController.bottomBarTab.mapEvents(Internal::ObserveTabSuccess)
    }
  }
}
