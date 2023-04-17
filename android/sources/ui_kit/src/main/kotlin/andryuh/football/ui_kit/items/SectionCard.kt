package andryuh.football.ui_kit.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.theme.Style12500
import andryuh.football.ui_kit.theme.Style14400

@Composable
fun SectionCard(
  modifier: Modifier = Modifier,
  title: String,
  contentPadding: PaddingValues = PaddingValues(20.dp),
  titlePadding: PaddingValues = PaddingValues(0.dp),
  content: @Composable SectionCardScope.() -> Unit,
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    backgroundColor = FootballColors.Surface1,
    elevation = 0.dp,
  ) {
    Column(
      modifier = Modifier.padding(contentPadding),
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      Text(
        modifier = Modifier.padding(titlePadding),
        text = title,
        color = FootballColors.Text.Secondary,
        style = Style12500,
      )
      content.invoke(SectionCardScope)
    }
  }
}

object SectionCardScope {

  @Composable
  fun SectionText(value: String) {
    Text(
      text = value,
      color = FootballColors.Text.Primary,
      style = Style14400,
    )
  }
}
