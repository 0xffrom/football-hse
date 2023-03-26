package goshka133.football.feature_profile.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import goshka133.football.ui_kit.R
import goshka133.football.ui_kit.theme.FootballColors
import goshka133.football.ui_kit.theme.Style16500

@Composable
fun Toolbar(
  onBackClick: () -> Unit,
  title: String,
) {
  Box(
    modifier = Modifier.systemBarsPadding().heightIn(min = 44.dp).padding(horizontal = 16.dp),
    contentAlignment = Alignment.CenterStart,
  ) {
    IconButton(onClick = onBackClick) {
      Icon(
        painter = painterResource(id = R.drawable.ic_24_back),
        contentDescription = null,
        tint = FootballColors.Icons.Primary,
      )
    }
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = title,
      textAlign = TextAlign.Center,
      color = FootballColors.Text.Primary,
      style = Style16500,
    )
  }
}
