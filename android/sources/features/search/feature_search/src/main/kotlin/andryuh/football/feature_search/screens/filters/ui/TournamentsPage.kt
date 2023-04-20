package andryuh.football.feature_search.screens.filters.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import andryuh.football.core_elmslie.EventReceiver
import andryuh.football.core_models.toTitle
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersEvent
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersEvent.Ui.Click
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersState
import andryuh.football.ui_kit.cell.FCell

@Composable
internal fun TournamentsPage(
  contentPadding: PaddingValues,
  stage: SearchFiltersState.Stage.Tournaments,
  eventReceiver: EventReceiver<SearchFiltersEvent>,
) {
  LazyColumn(modifier = Modifier.fillMaxWidth(), contentPadding = contentPadding) {
    item { Spacer(modifier = Modifier.height(8.dp)) }
    items(stage.tournamentsList) { (tournament, isChecked) ->
      FCell(
        onClick = { eventReceiver.invoke(Click.OnTournamentClick(tournament)) },
        content = { CheckBox(text = tournament.toTitle(), checked = isChecked) }
      )
      Spacer(modifier = Modifier.height(4.dp))
    }
    item { Spacer(modifier = Modifier.height(16.dp)) }
  }
}
