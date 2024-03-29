package andryuh.football.ui_kit.snack_bar

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

@Composable fun rememberSnackBarHostState() = remember { SnackbarHostState() }

val LocalSnackBarHostState =
  staticCompositionLocalOf<SnackbarHostState> { error("SnackbarHostState wasn't provided.") }

suspend fun SnackbarHostState.showError(error: Throwable) {
  error.message?.let { message -> showSnackbar(message) }
}
