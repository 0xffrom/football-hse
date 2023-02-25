package goshka133.football.core_navigation

import androidx.compose.runtime.compositionLocalOf
import com.github.terrakok.modo.NavigationContainer
import com.github.terrakok.modo.stack.StackState

val LocalRouter =
  compositionLocalOf<NavigationContainer<StackState>> { error("StackNavModel wasn't provided") }
