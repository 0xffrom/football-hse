package goshka133.football.core_di

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun <T> rememberDependencies(): T {
  val context = LocalContext.current

  return remember { getComponent(context) }
}

fun <T> getComponent(context: Context): T {
  return (context.applicationContext as DependenciesProvider).provide()
}
