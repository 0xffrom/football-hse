package andryuh.football.feature_search.screens.filters

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import andryuh.football.core_elmslie.rememberEventReceiver
import andryuh.football.core_elmslie.rememberStore
import andryuh.football.core_navigation.LocalRouter
import andryuh.football.domain_search.filters.Filter
import andryuh.football.domain_search.filters.FilterType
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersEffect
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersEvent.Ui.Click
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersState as State
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersStoreFactory
import andryuh.football.feature_search.screens.filters.ui.PositionsPage
import andryuh.football.feature_search.screens.filters.ui.TournamentsPage
import andryuh.football.ui_kit.animation.pageTransitionSpec
import andryuh.football.ui_kit.button.BottomBarStack
import andryuh.football.ui_kit.button.FButton
import andryuh.football.ui_kit.snack_bar.LocalSnackBarHostState
import andryuh.football.ui_kit.toolbar.Toolbar
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.back
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
internal class SearchFiltersScreen(
  val filter: Filter,
  val type: FilterType,
) : Screen(generateScreenKey()) {

  @OptIn(ExperimentalAnimationApi::class)
  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = SearchFiltersStoreFactory::class.java,
        storeProvider = { storeFactory, _ -> storeFactory.create(filter = filter, type = type) },
      )
    val eventReceiver = store.rememberEventReceiver()
    val state by store.states().collectAsState(store.currentState)
    val router = LocalRouter.current
    val snackBarHostState = LocalSnackBarHostState.current

    LaunchedEffect(Unit) {
      store.effects().collect { effect ->
        when (effect) {
          is SearchFiltersEffect.Close -> router.back()
          is SearchFiltersEffect.ShowError -> {
            CoroutineScope(Dispatchers.Main).launch {
              effect.error.message?.let { snackBarHostState.showSnackbar(it) }
            }
          }
        }
      }
    }

    Scaffold(
      modifier = Modifier.fillMaxSize(),
      topBar = {
        Toolbar(
          onBackClick = { eventReceiver.invoke(Click.Back) },
          title =
            when (state.currentPageType) {
              State.Stage.Type.Tournaments -> "Турниры"
              State.Stage.Type.Positions -> "Позиции"
            },
        )
      },
      bottomBar = {
        BottomBarStack {
          FButton(
            text =
              when (state.currentPageType) {
                State.Stage.Type.Tournaments -> "Применить"
                State.Stage.Type.Positions -> "Продолжить"
              },
            isLoading = state.isLoading,
          ) {
            eventReceiver(Click.Continue)
          }
        }
      },
    ) { contentPadding ->
      AnimatedContent(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        targetState = state.currentPageType,
        contentAlignment = Alignment.Center,
        transitionSpec =
          pageTransitionSpec(
            isLeftToRightSlideProvider = { state.currentPageType == State.Stage.Type.Positions },
          ),
        label = "/**/",
      ) { page ->
        when (page) {
          State.Stage.Type.Positions -> {
            PositionsPage(
              contentPadding = contentPadding,
              stage = state.positionsStage,
              eventReceiver = eventReceiver,
            )
          }
          State.Stage.Type.Tournaments -> {
            TournamentsPage(
              contentPadding = contentPadding,
              stage = state.tournamentsStage,
              eventReceiver = eventReceiver,
            )
          }
        }
      }
    }
  }
}
