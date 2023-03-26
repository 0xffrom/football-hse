package goshka133.football.feature_search.screens.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import goshka133.football.core_models.joinTitleToString
import goshka133.football.core_models.mapToTitle
import goshka133.football.domain_team.TeamApplication
import goshka133.football.feature_search.R
import goshka133.football.ui_kit.theme.CaptionMMedium
import goshka133.football.ui_kit.theme.FootballColors

@Composable
fun TeamApplicationCard(
  application: TeamApplication,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    backgroundColor = FootballColors.Surface1,
    elevation = 0.dp,
  ) {
    Column(
      modifier = Modifier.clickable(onClick = onClick).padding(vertical = 20.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
      ) {
        Row(
          modifier = Modifier.weight(1f).padding(start = 20.dp),
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          AsyncImage(
            modifier = Modifier.size(34.dp),
            model = application.imageUrl,
            contentDescription = null,
          )
          Text(
            text = application.name,
            color = FootballColors.Text.Primary,
            style = CaptionMMedium,
          )
        }
        IconButton(
          modifier = Modifier.padding(top = 4.dp, end = 20.dp).size(24.dp),
          onClick = { /*TODO*/}
        ) {
          Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = R.drawable.ic_20_more),
            contentDescription = null,
            tint = FootballColors.Icons.Tertiary,
          )
        }
      }

      val tournaments =
        remember(application.tournaments) {
          application.tournaments.joinTitleToString(separator = ", ")
        }

      if (tournaments.isNotEmpty()) {
        Text(
          modifier = Modifier.padding(horizontal = 20.dp),
          text = tournaments,
          color = FootballColors.Text.Secondary,
          style =
            TextStyle(
              fontFamily = FontFamily.Default,
              fontWeight = FontWeight.W400,
              fontSize = 12.sp,
              lineHeight = 15.sp,
              letterSpacing = (0.10).sp,
            ),
        )
      }

      val positionTitles =
        remember(application.playerPosition) { application.playerPosition.mapToTitle() }

      if (positionTitles.isNotEmpty()) {
        LazyRow(
          contentPadding = PaddingValues(horizontal = 20.dp),
          horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
          items(positionTitles) { positionTitle ->
            Text(
              modifier =
                Modifier.background(
                    color = Color(0x1A4258FE),
                    shape = RoundedCornerShape(4.dp),
                  )
                  .padding(horizontal = 6.dp, vertical = 2.dp),
              text = positionTitle,
              color = Color(0xFF3461FD),
              style =
                TextStyle(
                  fontWeight = FontWeight.W500,
                  fontSize = 12.sp,
                  lineHeight = 18.sp,
                  letterSpacing = 1.sp,
                ),
            )
          }
        }
      }
    }
  }
}
