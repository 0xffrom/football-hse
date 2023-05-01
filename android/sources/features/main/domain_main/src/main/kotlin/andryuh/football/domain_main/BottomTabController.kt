package andryuh.football.domain_main

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Singleton
class BottomTabController @Inject constructor() {

  private val _bottomBarTab = MutableStateFlow(CommonBottomBarTabType.SearchTeam)
  val bottomBarTab: StateFlow<CommonBottomBarTabType> = _bottomBarTab

  fun change(barTabType: CommonBottomBarTabType) {
    _bottomBarTab.value = barTabType
  }
}

enum class CommonBottomBarTabType {
  SearchTeam,
  SearchPlayer,
  Chat,
  Profile,
}
