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
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersEvent.Ui.Click.*
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersState
import andryuh.football.ui_kit.cell.FCell

@Composable
internal fun PositionsPage(
  contentPadding: PaddingValues,
  stage: SearchFiltersState.Stage.Positions,
  eventReceiver: EventReceiver<SearchFiltersEvent>,
) {
  LazyColumn(modifier = Modifier.fillMaxWidth(), contentPadding = contentPadding) {
    item { Spacer(modifier = Modifier.height(8.dp)) }
    items(stage.positionsList) { (position, isChecked) ->
      FCell(
        modifier = Modifier.fillMaxWidth(),
        onClick = { eventReceiver.invoke(OnPositionClick(position)) },
        content = { CheckBox(text = position.toTitle(), checked = isChecked) }
      )
      Spacer(modifier = Modifier.height(4.dp))
    }
    item { Spacer(modifier = Modifier.height(16.dp)) }
  }
}
