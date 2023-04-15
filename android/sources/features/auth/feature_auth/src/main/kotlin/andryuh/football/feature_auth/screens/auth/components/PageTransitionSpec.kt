package andryuh.football.feature_auth.screens.auth.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

private const val AnimationDurationMillis = 450

@OptIn(ExperimentalAnimationApi::class)
fun <T> pageTransitionSpec(
  isLeftToRightSlideProvider: () -> Boolean,
): AnimatedContentScope<T>.() -> ContentTransform {
  return {
    val coefficient = if (isLeftToRightSlideProvider.invoke()) -1 else 1

    val enterTransition =
      slideInHorizontally(
        animationSpec = tween(AnimationDurationMillis),
        initialOffsetX = { fullWidth -> coefficient * fullWidth },
      )

    val exitTransition =
      slideOutHorizontally(
        animationSpec = tween(AnimationDurationMillis),
        targetOffsetX = { fullWidth -> coefficient * -fullWidth },
      )

    enterTransition with exitTransition
  }
}
