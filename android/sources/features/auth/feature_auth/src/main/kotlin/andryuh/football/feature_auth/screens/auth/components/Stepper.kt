package andryuh.football.feature_auth.screens.auth.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import andryuh.football.ui_kit.theme.FootballColors

@Composable
fun Stepper(modifier: Modifier = Modifier, state: StepperState) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    val iterator = remember(state.stepsCount) { 0 until state.stepsCount }

    val currentStepIndex = state.currentStep - 1

    val color =
      iterator.map { stepIndex ->
        animateColorAsState(
          targetValue =
            if (stepIndex == currentStepIndex) {
              FootballColors.Primary
            } else {
              FootballColors.Surface2
            },
        )
      }

    for (step in iterator) {
      Box(
        modifier =
          Modifier.size(width = 32.dp, height = 4.dp)
            .background(
              color[step].value,
              shape = RoundedCornerShape(2.dp),
            )
      )
    }
  }
}

@Stable
data class StepperState(
  val stepsCount: Int,
) {

  var currentStep by mutableStateOf(1)
}
