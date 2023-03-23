package goshka133.football.feature_profile.screens.profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import goshka133.football.core_models.TeamApplication
import goshka133.football.core_models.TeamStatus
import goshka133.football.ui_kit.R
import goshka133.football.ui_kit.theme.FootballColors
import goshka133.football.ui_kit.theme.Style14400
import goshka133.football.ui_kit.theme.Style16500

@Composable
fun TeamCreationApplicationCard(
  modifier: Modifier,
  teamApplication: TeamApplication,
  onClick: () -> Unit,
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    backgroundColor = Color(0xFFF5F9FE),
    elevation = 0.dp,
  ) {
    Row(
      modifier = Modifier.clickable(onClick = onClick).padding(12.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      when (teamApplication) {
        is TeamApplication.NotRegistered -> {
          Image(
            modifier = Modifier.size(44.dp),
            painter = painterResource(id = R.drawable.img_document),
            contentDescription = null,
          )
          Text(
            modifier = Modifier.weight(1f),
            text = "Зарегистрировать команду",
            color = FootballColors.Text.Primary,
            textAlign = TextAlign.Start,
            style = Style16500,
          )
        }
        is TeamApplication.Registered -> {
          Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
          ) {
            when (teamApplication.team.status) {
              TeamStatus.OnValidation -> {
                Text(
                  text = "Регистрация команды",
                  color = FootballColors.Text.Primary,
                  textAlign = TextAlign.Start,
                  style = Style16500,
                )
                Text(
                  text = "Заявка обрабатывается",
                  color = FootballColors.Accent.Orange,
                  textAlign = TextAlign.Start,
                  style = Style14400,
                )
              }
              TeamStatus.Verified,
              TeamStatus.Declined -> {
                Text(
                  text = "Моя команда",
                  color = FootballColors.Text.Primary,
                  textAlign = TextAlign.Start,
                  style = Style16500,
                )
                Text(
                  text = teamApplication.team.name,
                  color = FootballColors.Text.Secondary,
                  textAlign = TextAlign.Start,
                  style = Style14400,
                )
              }
            }
          }
        }
      }

      Icon(
        painter = painterResource(id = R.drawable.ic_24_arrow_right),
        contentDescription = null,
        tint = FootballColors.Icons.Secondary,
      )
    }
  }
}
