package andryuh.football.feature_chat.screens.chat.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import andryuh.football.domain_chat.dto.Conversation
import andryuh.football.ui_kit.R
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.theme.Style12500
import andryuh.football.ui_kit.theme.Style14500
import andryuh.football.ui_kit.theme.Style16400
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.placeholder
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
internal fun ConversationCard(
  conversation: Conversation,
  onClick: (conversation: Conversation) -> Unit,
) {
  Row(
    modifier =
      Modifier.clickable(onClick = { onClick.invoke(conversation) })
        .padding(vertical = 12.dp, horizontal = 16.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    AsyncImage(
      modifier = Modifier.size(34.dp).clip(CircleShape),
      model = conversation.photoUrl,
      contentDescription = null,
      contentScale = ContentScale.Crop,
      error = painterResource(R.drawable.ic_60_profile_placeholder)
    )
    Column(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
      Text(
        text = conversation.name,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = FootballColors.Text.Primary,
        style = Style16400,
        fontWeight = FontWeight.Bold,
      )
      Text(
        text = conversation.lastMessage?.content ?: "Начните чат прямо сейчас!",
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = FootballColors.Text.Secondary,
        style = Style14500,
      )
    }
    val lastMessage = conversation.lastMessage
    if (lastMessage != null) {
      Column(
        modifier = Modifier.fillMaxHeight(),
      ) {
        val time = lastMessage.sendTime.time
        val timeFormat =
          remember(time) {
            LocalTime.of(time.hour, time.minute).format(DateTimeFormatter.ofPattern("hh:mm"))
          }

        Text(
          text = timeFormat,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          color = FootballColors.Text.Secondary,
          style = Style12500,
        )
      }
    }
  }
}

@Composable
internal fun ConversationCardShimmer() {
  Row(
    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Box(
      modifier =
        Modifier.size(34.dp)
          .clip(CircleShape)
          .placeholder(
            visible = true,
            highlight = PlaceholderHighlight.fade(),
          ),
    )
    Column(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
      Text(
        modifier =
          Modifier.width(240.dp)
            .placeholder(
              visible = true,
              highlight = PlaceholderHighlight.fade(),
            ),
        text = "",
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = FootballColors.Text.Primary,
        style = Style14500,
        fontWeight = FontWeight.Bold,
      )
      Text(
        modifier =
          Modifier.width(120.dp)
            .placeholder(
              visible = true,
              highlight = PlaceholderHighlight.fade(),
            ),
        text = "",
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = FootballColors.Text.Secondary,
        style = Style12500,
      )
    }
  }
}
