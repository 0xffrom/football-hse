package andryuh.football.feature_search.screens.search.presentation

import andryuh.football.domain_search.SearchRepository
import andryuh.football.feature_search.screens.search.presentation.SearchCommand as Command
import andryuh.football.feature_search.screens.search.presentation.SearchEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import vivid.money.elmslie.coroutines.Actor

internal class SearchActor
@Inject
constructor(
  private val searchRepository: SearchRepository,
) : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> {
    return when (command) {
      is Command.ObserveTeamApplications ->
        searchRepository
          .observeTeamApplications()
          .mapEvents(
            Internal::ObserveTeamApplicationsSuccess,
            Internal::ObserveTeamApplicationsError
          )
    }
  }
}
