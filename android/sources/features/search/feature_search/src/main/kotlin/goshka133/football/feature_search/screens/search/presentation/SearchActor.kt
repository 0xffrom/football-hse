package goshka133.football.feature_search.screens.search.presentation

import goshka133.football.feature_search.screens.search.presentation.SearchCommand as Command
import goshka133.football.feature_search.screens.search.presentation.SearchEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import vivid.money.elmslie.coroutines.Actor

internal class SearchActor @Inject constructor() : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> = emptyFlow()
}
