package andryuh.football.ui_kit.cell

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.theme.Style17400

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FCell(
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
  content: @Composable CellScope.() -> Unit,
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(12.dp),
    backgroundColor = FootballColors.Surface1,
    elevation = 0.dp,
    onClick = onClick,
  ) {
    content.invoke(CellScope)
  }
}

object CellScope {

  @Composable
  fun CheckBox(
    text: String,
    checked: Boolean,
  ) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        modifier = Modifier.weight(1f),
        text = text,
        color = FootballColors.Text.Primary,
        style = Style17400
      )
      Checkbox(
        checked = checked,
        onCheckedChange = null,
        colors =
          CheckboxDefaults.colors(
            checkedColor = FootballColors.Primary,
            uncheckedColor = Color(0x1122331A),
            checkmarkColor = Color.White,
          ),
      )
    }
  }
}
