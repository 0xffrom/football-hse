package goshka133.football.ui_kit.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomBarStack(
  backgroundColor: Color? = null,
  content: @Composable ColumnScope.() -> Unit,
) {
  Column(
    modifier =
      Modifier.navigationBarsPadding()
        .imePadding()
        .then(backgroundColor?.let { Modifier.background(it) } ?: Modifier)
        .padding(PaddingValues(top = 0.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    content.invoke(this)
  }
}
