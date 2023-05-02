package andryuh.football.feature_chat.screens.conversation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import andryuh.football.feature_chat.screens.conversation.presentation.ConversationState
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.theme.Style14400
import andryuh.football.ui_kit.theme.Style19600
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal val TimeFormatter = DateTimeFormatter.ofPattern("hh:mm")

@Composable
internal fun MessageGroupBubble(
  modifier: Modifier = Modifier,
  messageGroup: ConversationState.MessageGroup,
) {
  val dateFormatted =
    remember(messageGroup.date) {
      messageGroup.date?.format(TimeFormatter) ?: LocalDateTime.now().format(TimeFormatter)
    }

  when (messageGroup) {
    is ConversationState.MessageGroup.Common.ToUser -> {
      MessageGroupBubbleContent(
        modifier = modifier,
        backgroundColor = Color.White,
        type = BubbleType.LeftToRight,
        text = messageGroup.message.content,
        dateFormatted = dateFormatted,
      )
    }
    is ConversationState.MessageGroup.Common.FromUser -> {
      MessageGroupBubbleContent(
        modifier = modifier,
        backgroundColor = Color(0xFFECEBEB),
        type = BubbleType.RightToLeft,
        text = messageGroup.message.content,
        dateFormatted = dateFormatted,
      )
    }
    is ConversationState.MessageGroup.Temporary -> {
      MessageGroupBubbleContent(
        modifier =
          modifier.placeholder(
            visible = true,
            highlight = PlaceholderHighlight.fade(),
          ),
        backgroundColor = Color(0xFFECEBEB),
        type = BubbleType.RightToLeft,
        text = messageGroup.message.content,
        dateFormatted = dateFormatted,
      )
    }
  }
}

@Composable
private fun MessageGroupBubbleContent(
  modifier: Modifier = Modifier,
  backgroundColor: Color,
  type: BubbleType,
  dateFormatted: String,
  text: String,
) {
  if (text.isBlank()) return

  Box(
    modifier = Modifier.fillMaxWidth(),
    contentAlignment =
      if (type == BubbleType.RightToLeft) Alignment.BottomEnd else Alignment.BottomStart
  ) {
    val shape =
      RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = if (type == BubbleType.LeftToRight) 0.dp else 16.dp,
        bottomEnd = if (type == BubbleType.RightToLeft) 0.dp else 16.dp,
      )

    Row(
      modifier =
        modifier
          .widthIn(min = 32.dp, max = 320.dp)
          .background(
            color = backgroundColor,
            shape = shape,
          )
          .then(
            if (backgroundColor == Color.White) {
              Modifier.border(
                border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f)),
                shape = shape,
              )
            } else {
              Modifier
            }
          )
          .padding(horizontal = 12.dp, vertical = 8.dp),
      verticalAlignment = Alignment.Bottom,
      horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Text(
        modifier = Modifier.weight(weight = 1f, fill = false),
        text = text,
        color = FootballColors.Text.Primary,
        style = Style19600,
      )

      Text(
        text = dateFormatted,
        color = FootballColors.Text.Primary,
        style = Style14400,
      )
    }
  }
}

enum class BubbleType {
  LeftToRight,
  RightToLeft,
}
