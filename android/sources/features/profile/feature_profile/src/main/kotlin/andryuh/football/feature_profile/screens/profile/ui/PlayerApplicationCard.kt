package andryuh.football.feature_profile.screens.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import andryuh.football.core_models.joinTitleToString
import andryuh.football.core_models.toTitle
import andryuh.football.domain_profile.dto.PlayerApplication
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.theme.Style12500
import andryuh.football.ui_kit.theme.Style16400
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun PlayerApplicationCard(
  modifier: Modifier,
  application: PlayerApplication,
  onClick: () -> Unit,
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    backgroundColor = FootballColors.Surface1,
    elevation = 0.dp,
    shape = RoundedCornerShape(12.dp),
    onClick = onClick,
  ) {
    Column {
      Text(
        modifier =
          Modifier.fillMaxWidth()
            .padding(
              top = 16.dp,
              start = 16.dp,
              end = 16.dp,
            ),
        text = application.preferredTournaments.joinTitleToString(),
        textAlign = TextAlign.Start,
        color = FootballColors.Text.Primary,
        style = Style16400,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
      Spacer(modifier = Modifier.height(16.dp))
      LazyRow(
        modifier = Modifier.padding(bottom = 16.dp),
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
      ) {
        items(application.footballPosition) { positionTitle ->
          Text(
            modifier =
              Modifier.background(
                  color = Color(0x1A4258FE),
                  shape = RoundedCornerShape(4.dp),
                )
                .padding(horizontal = 6.dp, vertical = 2.dp),
            text = positionTitle.toTitle(),
            color = Color(0xFF3461FD),
            style = Style12500,
          )
        }
      }
    }
  }
}

@Composable
internal fun PlayerApplicationCardShimmer(modifier: Modifier) {
  Card(
    modifier = modifier.fillMaxWidth().height(96.dp),
    backgroundColor = FootballColors.Surface1,
    elevation = 0.dp,
    shape = RoundedCornerShape(12.dp),
  ) {
    Spacer(
      modifier =
        Modifier.fillMaxSize()
          .placeholder(
            visible = true,
            highlight = PlaceholderHighlight.fade(),
          )
    )
  }
}
