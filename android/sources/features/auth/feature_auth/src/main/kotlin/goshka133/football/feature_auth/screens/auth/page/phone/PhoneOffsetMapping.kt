package goshka133.football.feature_auth.screens.auth.page.phone

import androidx.compose.ui.text.input.OffsetMapping

internal class PhoneOffsetMapping : OffsetMapping {
  override fun originalToTransformed(offset: Int): Int {
    return when {
      offset == 0 -> 2
      offset < 4 -> offset + 3
      offset < 7 -> offset + 4
      offset < 9 -> offset + 5
      offset < 11 -> offset + 6
      else -> offset
    }
  }

  override fun transformedToOriginal(offset: Int): Int {
    return when {
      offset < 3 -> 0
      offset < 8 -> offset - 3
      offset < 12 -> offset - 4
      offset < 14 -> offset - 5
      offset <= 16 -> offset - 6
      else -> offset
    }
  }
}
