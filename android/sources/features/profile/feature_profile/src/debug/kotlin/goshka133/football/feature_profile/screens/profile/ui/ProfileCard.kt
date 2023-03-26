package goshka133.football.feature_profile.screens.profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import goshka133.football.domain_profile.dto.Profile
import goshka133.football.feature_profile.R
import goshka133.football.ui_kit.theme.FootballColors

@Composable
internal fun ProfileCard(
  modifier: Modifier,
  profile: Profile,
  onEditClick: () -> Unit,
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    backgroundColor = Color(0xFFF5F9FE),
    elevation = 0.dp,
  ) {
    Box(
      modifier =
        Modifier.padding(
          horizontal = 12.dp,
          vertical = 20.dp,
        ),
    ) {
      IconButton(modifier = Modifier.align(Alignment.TopEnd), onClick = onEditClick) {
        Icon(
          modifier = Modifier.size(24.dp),
          painter = painterResource(id = R.drawable.ic_24_pen),
          contentDescription = null,
        )
      }
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (profile.imageUrl.isNullOrBlank()) {
          Image(
            modifier = Modifier.size(56.dp).clip(RoundedCornerShape(16.dp)),
            painter = painterResource(id = R.drawable.img_default_profile),
            contentDescription = null,
          )
        } else {
          AsyncImage(
            modifier = Modifier.size(56.dp).clip(RoundedCornerShape(16.dp)),
            model = profile.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
          )
        }
        if (profile.isCaptain) {
          Text(
            modifier =
              Modifier.padding(vertical = 12.dp)
                .background(
                  color = Color(0x1A4258FE),
                  shape = RoundedCornerShape(4.dp),
                )
                .padding(horizontal = 6.dp, vertical = 2.dp),
            text = "Капитан",
            color = Color(0xFF3461FD),
            style =
              TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 1.sp,
              ),
          )
        } else {

          Spacer(modifier = Modifier.height(16.dp))
        }
        Text(
          modifier = Modifier.fillMaxWidth(),
          text = profile.fullName,
          textAlign = TextAlign.Center,
          color = FootballColors.Text.Secondary,
          style =
            TextStyle(
              fontFamily = FontFamily.Default,
              fontWeight = FontWeight.W600,
              fontSize = 19.sp,
              lineHeight = 24.sp,
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
          verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
          BlockInfo(
            title = "Футбольный опыт",
            info = profile.footballExperience,
          )
          BlockInfo(
            title = "Опыт в турнирах НИУ ВШЭ",
            info = profile.tournamentsExperience,
          )
          BlockInfo(
            title = "Контактная информация",
            info = profile.contactInfo,
          )
          BlockInfo(
            title = "О себе",
            info = profile.about,
          )
        }
      }
    }
  }
}

@Composable
private fun BlockInfo(
  title: String,
  info: String,
) {
  if (info.isNotBlank()) {
    Column(
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        text = title,
        color = FootballColors.Text.Tertiary,
        style =
          TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            lineHeight = 18.sp,
          ),
      )
      Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        text = info,
        color = FootballColors.Text.Primary,
        style =
          TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            lineHeight = 18.sp,
          ),
      )
    }
  }
}
