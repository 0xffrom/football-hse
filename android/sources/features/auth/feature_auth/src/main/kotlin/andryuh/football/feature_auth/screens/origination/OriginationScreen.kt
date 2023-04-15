package andryuh.football.feature_auth.screens.origination

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.github.terrakok.modo.stack.back
import com.github.terrakok.modo.stack.newStack
import andryuh.football.core_di.rememberDependencies
import andryuh.football.core_elmslie.rememberStore
import andryuh.football.core_navigation.LocalRouter
import andryuh.football.feature_auth.di.AuthFeatureDependencies
import andryuh.football.feature_auth.screens.origination.components.RoleCard
import andryuh.football.feature_auth.screens.origination.presentation.OriginationEffect
import andryuh.football.feature_auth.screens.origination.presentation.OriginationEvent
import andryuh.football.feature_auth.screens.origination.presentation.OriginationEvent.Ui.Click
import andryuh.football.feature_auth.screens.origination.presentation.OriginationStoreFactory
import andryuh.football.ui_kit.BaseScreen
import andryuh.football.ui_kit.button.BottomBarStack
import andryuh.football.ui_kit.button.FButton
import andryuh.football.ui_kit.snack_bar.LocalSnackBarHostState
import andryuh.football.ui_kit.text_field.FTextField
import andryuh.football.ui_kit.theme.CaptionMRegular
import andryuh.football.ui_kit.theme.CaptionSRegular
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.theme.Large
import kotlinx.parcelize.Parcelize

internal typealias EventReceiver = (event: OriginationEvent) -> Unit

@Parcelize
internal class OriginationScreen : BaseScreen() {

  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = OriginationStoreFactory::class.java,
        storeProvider = { factory, _ -> factory.create() },
      )

    val state = store.states().collectAsState(initial = store.currentState).value

    val router = LocalRouter.current
    val snackbarHostState = LocalSnackBarHostState.current

    val dependencies: AuthFeatureDependencies = rememberDependencies()
    LaunchedEffect(Unit) {
      store.effects().collect { effect ->
        when (effect) {
          is OriginationEffect.Close -> router.back()
          is OriginationEffect.ShowError -> {
            effect.error.message?.let { snackbarHostState.showSnackbar(it) }
          }
          is OriginationEffect.OpenMain -> {
            router.newStack(dependencies.mainFeatureApi.getScreen())
          }
        }
      }
    }

    val eventReceiver: EventReceiver = remember(store) { { event -> store.accept(event) } }

    BackHandler { eventReceiver.invoke(Click.Back) }

    Scaffold(
      bottomBar = {
        BottomBarStack(
          backgroundColor = Color.White,
        ) {
          FButton(
            text = "Продолжить",
            isLoading = state.isLoading,
          ) {
            eventReceiver(Click.Continue)
          }
          Text(
            modifier = Modifier.fillMaxWidth(),
            text =
              "Нажимая кнопку Продолжить, я даю согласие на хранение и обработку персональных данных",
            textAlign = TextAlign.Start,
            color = FootballColors.Text.Secondary,
            style = CaptionSRegular,
          )
        }
      }
    ) { contentPadding ->
      LazyVerticalGrid(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        contentPadding = contentPadding,
        columns = GridCells.Fixed(2),
      ) {
        item(span = { GridItemSpan(2) }) {
          Spacer(
            modifier = Modifier.statusBarsPadding().height(74.dp),
          )
        }
        item(span = { GridItemSpan(2) }) {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Последний шаг",
            textAlign = TextAlign.Start,
            color = FootballColors.Text.Primary,
            style = Large,
          )
        }
        item(span = { GridItemSpan(2) }) { Spacer(modifier = Modifier.height(12.dp)) }
        item(span = { GridItemSpan(2) }) {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text =
              "Заполните данные, чтобы мы могли определить вашу роль в команде или подобрать вам подходящую команду",
            textAlign = TextAlign.Start,
            color = FootballColors.Text.Primary,
            style = CaptionMRegular,
          )
        }
        item(span = { GridItemSpan(2) }) { Spacer(modifier = Modifier.height(32.dp)) }
        item(span = { GridItemSpan(2) }) {
          val focusRequester = remember { FocusRequester() }

          LaunchedEffect(Unit) { focusRequester.requestFocus() }

          FTextField(
            modifier = Modifier.focusRequester(focusRequester),
            value = state.nameTextFieldValue,
            onValueChange = { textFieldValue ->
              eventReceiver.invoke(
                OriginationEvent.Ui.Action.OnNameTextFieldValueChanged(textFieldValue)
              )
            },
            keyboardOptions =
              remember {
                KeyboardOptions(
                  keyboardType = KeyboardType.Text,
                  autoCorrect = true,
                  capitalization = KeyboardCapitalization.Words,
                  imeAction = ImeAction.Next,
                )
              },
            placeholder = "Введите ФИО",
            isError = state.isNameTextFieldError,
          )
        }
        item(span = { GridItemSpan(2) }) { Spacer(modifier = Modifier.height(12.dp)) }
        item(span = { GridItemSpan(2) }) {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Выберите Вашу роль в НИУ ВШЭ",
            textAlign = TextAlign.Start,
            color = FootballColors.Text.Secondary,
            style = CaptionMRegular,
          )
        }
        item(span = { GridItemSpan(2) }) { Spacer(modifier = Modifier.height(16.dp)) }

        state.roleCards.fastForEachIndexed { index, roleCard ->
          item {
            Row(modifier = Modifier.padding(bottom = 12.dp)) {
              if (index % 2 == 1) {
                Spacer(modifier = Modifier.width(6.dp))
              }
              RoleCard(
                modifier = Modifier.weight(1f),
                card = roleCard,
                isSelected = state.selectedRoleType == roleCard.type,
                onClick =
                  remember(store) { { type -> store.accept(Click.RoleCard(roleType = type)) } },
              )
              if (index % 2 == 0) {
                Spacer(modifier = Modifier.width(6.dp))
              }
            }
          }
        }
      }
    }
  }
}
