package andryuh.football.feature_search.screens.search_player.presentation

import andryuh.football.domain_search.SearchRepository
import andryuh.football.feature_search.screens.search_player.presentation.SearchPlayerCommand as Command
import andryuh.football.feature_search.screens.search_player.presentation.SearchPlayerEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import vivid.money.elmslie.coroutines.Actor

internal class SearchPlayerActor
@Inject
constructor(
  private val searchPlayerRepository: SearchRepository,
) : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> {
    return when (command) {
      is Command.ObservePlayerApplications ->
        searchPlayerRepository
          .observeAllPlayerApplications()
          .mapEvents(
            eventMapper = Internal::ObservePlayerApplicationsSuccess,
            errorMapper = Internal::ObservePlayerApplicationsError
          )
      is Command.ObserveFilter ->
        searchPlayerRepository
          .observeCommandsFilter()
          .mapEvents(
            eventMapper = Internal::ObserveFilterSuccess,
          )
    }
  }
}
