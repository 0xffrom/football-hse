package andryuh.football.feature_main.screens.main.models

import androidx.annotation.DrawableRes
import andryuh.football.domain_main.CommonBottomBarTabType
import andryuh.football.feature_auth.R

internal enum class BottomBarTabType(@DrawableRes val drawableRes: Int) {
  SearchTeam(R.drawable.ic_24_search),
  SearchPlayer(R.drawable.ic_24_user_plus),
  Chat(R.drawable.ic_24_chat),
  Profile(R.drawable.ic_24_user)
}

internal fun CommonBottomBarTabType.toTab() =
  when (this) {
    CommonBottomBarTabType.SearchTeam -> BottomBarTabType.SearchTeam
    CommonBottomBarTabType.SearchPlayer -> BottomBarTabType.SearchPlayer
    CommonBottomBarTabType.Chat -> BottomBarTabType.Chat
    CommonBottomBarTabType.Profile -> BottomBarTabType.Profile
  }

internal fun BottomBarTabType.toCommonTab() =
  when (this) {
    BottomBarTabType.SearchTeam -> CommonBottomBarTabType.SearchTeam
    BottomBarTabType.SearchPlayer -> CommonBottomBarTabType.SearchPlayer
    BottomBarTabType.Chat -> CommonBottomBarTabType.Chat
    BottomBarTabType.Profile -> CommonBottomBarTabType.Profile
  }
