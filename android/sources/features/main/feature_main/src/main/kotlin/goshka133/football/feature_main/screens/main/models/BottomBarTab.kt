package goshka133.football.feature_main.screens.main.models

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import goshka133.football.feature_auth.R
import goshka133.football.ui_kit.theme.FootballColors

@Composable
fun RowScope.BottomBarTab(
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

enum class BottomBarTabType(@DrawableRes val drawableRes: Int) {
  Search(R.drawable.ic_24_search),
  Chat(R.drawable.ic_24_chat),
  Profile(R.drawable.ic_24_user)
}
