package goshka133.football.ui_kit.snack_bar

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

@Composable fun rememberSnackBarHostState() = remember { SnackbarHostState() }

val LocalSnackBarHostState =
  staticCompositionLocalOf<SnackbarHostState> { error("SnackbarHostState wasn't provided.") }
