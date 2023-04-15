package andryuh.football.feature_search.screens.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import andryuh.football.feature_search.R
import andryuh.football.ui_kit.borders.SelectedBorderModifier
import andryuh.football.ui_kit.theme.BodySemibold
import andryuh.football.ui_kit.theme.DefaultShapes
import andryuh.football.ui_kit.theme.FootballColors

@Composable
internal fun CreateApplicationBanner(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier =
      modifier
        .fillMaxWidth()
        .height(64.dp)
        .clip(shape = DefaultShapes.large)
        .background(color = FootballColors.Surface2)
        .then(SelectedBorderModifier)
        .clickable(onClick = onClick),
  ) {
    // TODO: use a brush with radial gradient instead of webp.
    Image(
      painter = painterResource(id = R.drawable.img_application_banner_background),
      contentDescription = null,
    )
    Row(
      horizontalArrangement = Arrangement.spacedBy(4.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Image(
        painter = painterResource(id = R.drawable.img_application_banner),
        contentDescription = null,
      )
      Text(
        modifier = Modifier.weight(1f),
        text = "Создать заявку на поиск игрока",
        textAlign = TextAlign.Center,
        color = FootballColors.Text.Primary,
        style = BodySemibold,
      )
    }
  }
}
