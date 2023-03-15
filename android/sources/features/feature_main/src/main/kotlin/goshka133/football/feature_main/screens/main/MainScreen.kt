package goshka133.football.feature_main.screens.main

import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import goshka133.football.ui_kit.BaseScreen
import goshka133.football.ui_kit.theme.FootballColors
import kotlinx.parcelize.Parcelize

@Parcelize
internal class MainScreen : BaseScreen() {

  @Composable
  override fun Content() {
    Scaffold(
      bottomBar = {
        BottomNavigation(
          elevation = 4.dp,
          backgroundColor = FootballColors.Background,
        ) {
          // TODO: in next MR
          //          BottomNavigationItem(
          //            selected = false,
          //            onClick = { /*TODO*/},
          //            icon = {
          //              Icon(
          //                painter = painterResource(id = R.drawable.ic_24_chat),
          //                contentDescription = null,
          //              )
          //            },
          //            selectedContentColor = FootballColors.Icons.Primary,
          //            unselectedContentColor = FootballColors.Icons.Tertiary
          //          )
        }
      }
    ) {
      //
    }
  }
}
