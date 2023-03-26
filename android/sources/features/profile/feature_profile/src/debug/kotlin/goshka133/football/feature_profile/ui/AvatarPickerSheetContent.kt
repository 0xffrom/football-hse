package goshka133.football.feature_profile.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import goshka133.football.ui_kit.R
import goshka133.football.ui_kit.button.ButtonStyle
import goshka133.football.ui_kit.button.FButton
import goshka133.football.ui_kit.theme.FootballColors

internal val DefaultSheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
internal val DefaultSheetBackgroundColor = Color.White

@Composable
internal fun AvatarPickerSheetContent(
  title: String,
  onContinueClick: () -> Unit,
  onCloseClick: () -> Unit,
) {
  BackHandler(onBack = onCloseClick)

  Column(
    modifier =
      Modifier.padding(horizontal = 16.dp, vertical = 12.dp).imePadding().navigationBarsPadding(),
  ) {
    Text(
      modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
      text = "Логотип",
      textAlign = TextAlign.Start,
      color = FootballColors.Text.Primary,
      style =
        TextStyle(
          fontWeight = FontWeight.W600,
          fontSize = 19.sp,
          lineHeight = 24.sp,
        ),
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = title,
      textAlign = TextAlign.Start,
      color = FootballColors.Text.Primary,
      style =
        TextStyle(
          fontWeight = FontWeight.W400,
          fontSize = 16.sp,
          lineHeight = 22.sp,
        ),
    )
    Spacer(modifier = Modifier.height(12.dp))
    FButton(text = "Выбрать", buttonStyle = ButtonStyle.Primary, onClick = onContinueClick)
    Spacer(modifier = Modifier.height(12.dp))
    FButton(text = "Отмена", buttonStyle = ButtonStyle.Secondary, onClick = onCloseClick)
  }
}

@Composable
internal fun AvatarNewPhotoBox(
  size: Dp,
  onClick: () -> Unit,
) {
  Box(
    modifier =
      Modifier.size(size)
        .clip(CircleShape)
        .background(color = FootballColors.Surface2, shape = CircleShape)
        .clickable(onClick = onClick),
    contentAlignment = Alignment.Center,
  ) {
    Icon(
      painter = painterResource(id = R.drawable.ic_24_plus),
      contentDescription = null,
      tint = FootballColors.Primary,
    )
  }
}
