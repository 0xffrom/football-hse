package andryuh.football.feature_search.screens.search_team.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import andryuh.football.core_elmslie.EventReceiver
import andryuh.football.core_models.toTitle
import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsEvent
import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsState
import andryuh.football.ui_kit.cell.FCell
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.theme.Style14500
import andryuh.football.ui_kit.theme.Style16400

@Composable
internal fun TournamentsPage(
  contentPadding: PaddingValues,
  stage: SearchTeamDetailsState.Stage.Tournaments,
  eventReceiver: EventReceiver<SearchTeamDetailsEvent>,
) {
  LazyColumn(modifier = Modifier.fillMaxWidth(), contentPadding = contentPadding) {
    item {
      Spacer(modifier = Modifier.height(12.dp))
      Text(
        text = "Предпочтительные турниры",
        color = FootballColors.Text.Secondary,
        style = Style14500,
      )
      Spacer(modifier = Modifier.height(12.dp))
    }
    items(stage.tournamentsList) { (tournament, isChecked) ->
      FCell(
        onClick = {
          eventReceiver.invoke(SearchTeamDetailsEvent.Ui.Click.OnTournamentClick(tournament))
        },
        content = { CheckBox(text = tournament.toTitle(), checked = isChecked) }
      )
      Spacer(modifier = Modifier.height(4.dp))
    }
    item {
      Spacer(modifier = Modifier.height(20.dp))
      Card(
        modifier = Modifier,
        backgroundColor = FootballColors.Surface2,
        shape = RoundedCornerShape(16.dp),
        elevation = 0.dp
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
          Canvas(modifier = Modifier.width(2.dp).height(64.dp)) {
            drawRoundRect(
              color = FootballColors.Primary,
              topLeft = Offset.Zero,
              cornerRadius = CornerRadius(16.dp.toPx()),
            )
          }
          Text(
            modifier = Modifier.weight(1f),
            text = "Дополнительная информация из вашего профиля также будет видна капитанам команд",
            color = FootballColors.Text.Secondary,
            style = Style16400,
          )
        }
      }
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}
