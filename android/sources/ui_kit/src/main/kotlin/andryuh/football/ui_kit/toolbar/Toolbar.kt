package andryuh.football.ui_kit.toolbar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import andryuh.football.ui_kit.R
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.theme.Style16500

@Composable
fun Toolbar(
  onBackClick: (() -> Unit)? = null,
  title: String,
  rightAccessor: (@Composable () -> Unit)? = null,
) {
  Row(
    modifier =
      Modifier
        .statusBarsPadding()
        .heightIn(min = 44.dp)
        .padding(horizontal = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Box(
      modifier = Modifier.weight(1f),
      contentAlignment = Alignment.CenterStart,
    ) {
      onBackClick?.let {
        IconButton(onClick = onBackClick) {
          Icon(
            painter = painterResource(id = R.drawable.ic_24_back),
            contentDescription = null,
            tint = FootballColors.Icons.Primary,
          )
        }
      }
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = title,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        color = FootballColors.Text.Primary,
        style = Style16500,
      )
    }

    rightAccessor?.invoke()
  }
}
