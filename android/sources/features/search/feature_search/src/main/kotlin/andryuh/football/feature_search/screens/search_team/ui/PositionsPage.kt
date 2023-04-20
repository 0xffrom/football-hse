package andryuh.football.feature_search.screens.search_team.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import andryuh.football.core_elmslie.EventReceiver
import andryuh.football.core_models.toTitle
import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsEvent
import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsEvent.Ui.Click
import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsState
import andryuh.football.ui_kit.cell.FCell
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.theme.Style12500
import andryuh.football.ui_kit.theme.Style14500

@Composable
internal fun PositionsPage(
  contentPadding: PaddingValues,
  stage: SearchTeamDetailsState.Stage.Positions,
  eventReceiver: EventReceiver<SearchTeamDetailsEvent>,
) {
  LazyColumn(
    modifier = Modifier.fillMaxWidth(),
    contentPadding = contentPadding
  ) {
    item {
      Spacer(modifier = Modifier.height(12.dp))
      Text(
        text = "Позиции",
        color = FootballColors.Text.Secondary,
        style = Style14500,
      )
      Spacer(modifier = Modifier.height(12.dp))
    }
    items(stage.positionsList) { (position, isChecked) ->
      FCell(
        modifier = Modifier.fillMaxWidth(),
        onClick = { eventReceiver.invoke(Click.OnPositionClick(position)) },
        content = { CheckBox(text = position.toTitle(), checked = isChecked) }
      )
      Spacer(modifier = Modifier.height(8.dp))
    }
    item { Spacer(modifier = Modifier.height(12.dp)) }
  }
}
