package andryuh.football.feature_search.screens.filters.presentation

import andryuh.football.domain_search.SearchRepository
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersCommand as Command
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor

internal class SearchFiltersActor
@Inject
constructor(
  private val searchRepository: SearchRepository,
) : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> {
    return when (command) {
      is Command.SaveFilter ->
        flow { emit(searchRepository.updateCommandsFilter(command.filter)) }
          .mapEvents(
            eventMapper = { Internal.SaveFilterSuccess },
          )
    }
  }
}
