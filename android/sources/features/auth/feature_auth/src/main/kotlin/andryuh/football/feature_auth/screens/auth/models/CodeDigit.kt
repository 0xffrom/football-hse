package andryuh.football.feature_auth.screens.auth.models

import androidx.compose.runtime.Immutable

@Immutable
data class CodeDigit(
  val number: String,
  val isEmpty: Boolean,
  val isSelected: Boolean,
)
