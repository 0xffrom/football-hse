package andryuh.football.feature_auth.screens.origination.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import andryuh.football.domain_profile.dto.RoleType
import andryuh.football.feature_auth.screens.origination.models.RoleCard
import andryuh.football.ui_kit.borders.SelectedBorderModifier
import andryuh.football.ui_kit.theme.BodyRegular
import andryuh.football.ui_kit.theme.DefaultShapes
import andryuh.football.ui_kit.theme.FootballColors

@Composable
internal fun RoleCard(
  card: RoleCard,
  isSelected: Boolean,
  onClick: (roleType: RoleType) -> Unit,
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(24.dp),
) {
  val borderModifier = if (isSelected) SelectedBorderModifier else Modifier

  Box(
    modifier =
      modifier
        .background(
          color = FootballColors.Surface1,
          shape = DefaultShapes.large,
        )
        .then(borderModifier)
        .clickable(onClick = { onClick.invoke(card.type) })
        .padding(contentPadding),
  ) {
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = card.title,
      textAlign = TextAlign.Center,
      color = FootballColors.Text.Secondary,
      style = BodyRegular,
    )
  }
}
