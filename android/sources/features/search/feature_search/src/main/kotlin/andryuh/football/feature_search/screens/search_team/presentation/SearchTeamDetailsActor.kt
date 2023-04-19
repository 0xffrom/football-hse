package andryuh.football.feature_search.screens.search_team.presentation

import andryuh.football.domain_search.SearchRepository
import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsCommand as Command
import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor

internal class SearchTeamDetailsActor
@Inject
constructor(
  private val searchRepository: SearchRepository,
) : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> {
    return when (command) {
      is Command.CreatePlayerApplication ->
        flow {
            emit(
              searchRepository.createCreatePlayerApplication(
                command.tournaments,
                command.positions,
              )
            )
          }
          .mapEvents(
            { Internal.CreatePlayerApplicationSuccess },
            Internal::CreatePlayerApplicationError,
          )
    }
  }
}
