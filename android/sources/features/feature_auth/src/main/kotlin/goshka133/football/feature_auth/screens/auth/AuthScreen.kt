package goshka133.football.feature_auth.screens.auth

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import com.github.terrakok.modo.stack.back
import goshka133.football.core_elmslie.rememberStore
import goshka133.football.core_navigation.LocalRouter
import goshka133.football.feature_auth.screens.auth.components.Stepper
import goshka133.football.feature_auth.screens.auth.components.StepperState
import goshka133.football.feature_auth.screens.auth.components.pageTransitionSpec
import goshka133.football.feature_auth.screens.auth.page.PhonePage
import goshka133.football.feature_auth.screens.auth.page.SmsPage
import goshka133.football.feature_auth.screens.auth.presentation.*
import goshka133.football.ui_kit.BaseScreen
import goshka133.football.ui_kit.button.BottomBarStack
import goshka133.football.ui_kit.button.FButton
import kotlinx.parcelize.Parcelize
import vivid.money.elmslie.coroutines.states

internal typealias EventReceiver = (event: AuthEvent) -> Unit

@Parcelize
internal class AuthScreen : BaseScreen() {

  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = AuthStoreFactory::class.java,
        storeProvider = { factory, _ -> factory.create() },
      )

    val router = LocalRouter.current

    LaunchedEffect(Unit) {
      store.effects().collect { effect ->
        when (effect) {
          is AuthEffect.Close -> router.back()
          is AuthEffect.OpenMoreInfo -> {
            // TODO
          }
        }
      }
    }

    val eventReceiver: EventReceiver = remember(store) { { event -> store.accept(event) } }

    BackHandler { eventReceiver.invoke(AuthEvent.Ui.Click.Back) }

    val state by store.states.collectAsState(store.currentState)

    Scaffold(
      topBar = {
        Box(
          modifier = Modifier.statusBarsPadding().fillMaxWidth().height(56.dp),
          contentAlignment = Alignment.Center,
        ) {
          val stepperState = remember { StepperState(stepsCount = PageNumbers) }

          LaunchedEffect(state.currentPage.number) {
            stepperState.currentStep = state.currentPage.number
          }

          Stepper(state = stepperState)
        }
      },
      bottomBar = {
        WindowInsetsControllerCompat
        WindowInsets.navigationBars
        BottomBarStack {
          FButton(
            text = "Продолжить",
            isLoading = state.isLoading,
          ) {
            eventReceiver(AuthEvent.Ui.Click.Continue)
          }
        }
      }
    ) { contentPadding ->
      @OptIn(ExperimentalAnimationApi::class)
      AnimatedContent(
        modifier = Modifier.padding(contentPadding).padding(horizontal = 16.dp).fillMaxSize(),
        targetState = state.currentNumberPage,
        contentAlignment = Alignment.Center,
        transitionSpec =
          pageTransitionSpec(
            isLeftToRightSlideProvider = { state.currentNumberPage < state.previousNumberPage },
          )
      ) { number ->
        when (state.getPageByNumber(number)) {
          is AuthState.Page.PhoneNumber -> {
            PhonePage(page = state.phoneNumberPage, eventReceiver = eventReceiver)
          }
          is AuthState.Page.SmsCode -> {
            SmsPage(page = state.smsCodePage, eventReceiver = eventReceiver)
          }
        }
      }
    }
  }
}
