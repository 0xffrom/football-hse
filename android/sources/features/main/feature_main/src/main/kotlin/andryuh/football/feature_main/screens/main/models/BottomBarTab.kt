package andryuh.football.feature_main.screens.main.models

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import andryuh.football.ui_kit.theme.FootballColors

@Composable
internal fun RowScope.BottomBarTab(
  type: BottomBarTabType,
  selectedType: BottomBarTabType,
  onClick: (type: BottomBarTabType) -> Unit,
) {
  BottomNavigationItem(
    selected = type == selectedType,
    onClick = { onClick.invoke(type) },
    icon = {
      Icon(
        painter = painterResource(id = type.drawableRes),
        contentDescription = null,
      )
    },
    selectedContentColor = FootballColors.Primary,
    unselectedContentColor = FootballColors.Icons.Tertiary
  )
}


