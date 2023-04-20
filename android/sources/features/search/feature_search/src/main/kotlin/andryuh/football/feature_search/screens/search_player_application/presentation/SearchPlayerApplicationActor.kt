package andryuh.football.feature_search.screens.search_player_application.presentation

import andryuh.football.domain_search.SearchRepository
import andryuh.football.feature_search.screens.search_player_application.presentation.SearchPlayerApplicationCommand as Command
import andryuh.football.feature_search.screens.search_player_application.presentation.SearchPlayerApplicationEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor

internal class SearchPlayerApplicationActor
@Inject
constructor(
  private val searchRepository: SearchRepository,
) : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> {
    return when (command) {
      is Command.CreateTeamApplication ->
        flow {
            emit(
              searchRepository.createCreateTeamApplication(
                command.tournaments,
                command.positions,
              )
            )
          }
          .mapEvents(
            { Internal.CreateTeamApplicationSuccess },
            Internal::CreateTeamApplicationError,
          )
    }
  }
}
