package andryuh.football.feature_search.screens.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import andryuh.football.core_di.rememberDependencies
import andryuh.football.core_elmslie.rememberEventReceiver
import andryuh.football.core_elmslie.rememberStore
import andryuh.football.core_kotlin.Resource
import andryuh.football.core_navigation.LocalRouter
import andryuh.football.domain_search.filters.FilterType
import andryuh.football.feature_search.R
import andryuh.football.feature_search.di.SearchFeatureDependencies
import andryuh.football.feature_search.screens.filters.SearchFiltersScreen
import andryuh.football.feature_search.screens.search.presentation.SearchEffect
import andryuh.football.feature_search.screens.search.presentation.SearchEvent
import andryuh.football.feature_search.screens.search.presentation.SearchStoreFactory
import andryuh.football.feature_search.screens.search.ui.CreateApplicationBanner
import andryuh.football.feature_search.screens.search.ui.TeamApplicationCard
import andryuh.football.feature_search.screens.search.ui.TeamApplicationCardShimmer
import andryuh.football.feature_search.screens.search_team.SearchTeamDetailsScreen
import andryuh.football.ui_kit.BaseScreen
import andryuh.football.ui_kit.snack_bar.LocalSnackBarHostState
import andryuh.football.ui_kit.text_field.FTextField
import andryuh.football.ui_kit.theme.BodyLarge
import andryuh.football.ui_kit.theme.BodySemibold
import andryuh.football.ui_kit.theme.FootballColors
import com.github.terrakok.modo.stack.forward
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
internal class SearchScreen : BaseScreen() {

  @OptIn(ExperimentalFoundationApi::class)
  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = SearchStoreFactory::class.java,
        storeProvider = { storeFactory, _ -> storeFactory.create() },
        savedStateRegistryOwner = LocalSavedStateRegistryOwner.current,
        vmStoreOwner = LocalViewModelStoreOwner.current!!,
      )
    val state by store.states().collectAsState(store.currentState)
    val dependencies: SearchFeatureDependencies = rememberDependencies()
    val router = LocalRouter.current
    val snackBarHostState = LocalSnackBarHostState.current
    LaunchedEffect(key1 = store) {
      store.effects().collect { effect ->
        when (effect) {
          is SearchEffect.OpenTeamApplicationDetails -> {
            router.forward(
              dependencies.teamFeatureApi.getTeamApplicationDetailsScreen(effect.application)
            )
          }
          is SearchEffect.ShowError -> {
            CoroutineScope(Dispatchers.Main).launch {
              effect.error.message?.let { snackBarHostState.showSnackbar(it) }
            }
          }
          is SearchEffect.OpenSearchTeamApplication -> {
            router.forward(SearchTeamDetailsScreen())
          }
          is SearchEffect.OpenFilters -> {
            router.forward(SearchFiltersScreen(filter = state.filter, type = FilterType.Commands))
          }
        }
      }
    }
    val eventReceiver = store.rememberEventReceiver()

    Column {
      Spacer(modifier = Modifier.statusBarsPadding().height(12.dp))
      Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        FTextField(
          modifier = Modifier.weight(1f),
          value = state.searchTextFieldValue,
          onValueChange = {
            eventReceiver.invoke(SearchEvent.Ui.Action.OnSearchTextFieldValueChange(it))
          },
          singleLine = true,
          minHeight = 38.dp,
          leadingIcon = {
            Icon(
              painter = painterResource(id = R.drawable.ic_16_search),
              contentDescription = null,
              tint = FootballColors.Icons.Secondary,
            )
          },
          placeholder = "Поиск по названию",
        )
        IconButton(onClick = { eventReceiver.invoke(SearchEvent.Ui.Click.Filter) }) {
          Icon(
            painter = painterResource(id = R.drawable.ic_20_filter),
            contentDescription = null,
            tint = FootballColors.Icons.Secondary,
          )
        }
      }
      Spacer(modifier = Modifier.height(16.dp))
      CreateApplicationBanner(
        modifier = Modifier.padding(horizontal = 16.dp),
        onClick = { eventReceiver.invoke(SearchEvent.Ui.Click.CreateApplicationBanner) },
      )
      Spacer(modifier = Modifier.height(24.dp))
      Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = "Заявки команд",
        color = FootballColors.Text.Primary,
        style = BodyLarge,
      )
      when (state.applications) {
        is Resource.Loading -> {
          LazyColumn(
            modifier = Modifier.fillMaxWidth(),
          ) {
            item { Spacer(modifier = Modifier.height(12.dp)) }
            items(3) {
              TeamApplicationCardShimmer(
                modifier = Modifier.padding(horizontal = 16.dp),
              )
              Spacer(modifier = Modifier.height(12.dp))
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
          }
        }
        is Resource.Data -> {
          if (state.filteredApplications.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn {
              items(state.filteredApplications, key = { it.id!! }) { application ->
                TeamApplicationCard(
                  modifier = Modifier.padding(horizontal = 16.dp).animateItemPlacement(),
                  application = application,
                  onClick = {
                    eventReceiver.invoke(
                      SearchEvent.Ui.Click.TeamApplicationCard(application = application)
                    )
                  }
                )
                Spacer(modifier = Modifier.height(12.dp))
              }
            }
          } else {
            Column(
              modifier = Modifier.fillMaxSize(),
              verticalArrangement =
                Arrangement.spacedBy(
                  space = 8.dp,
                  alignment = BiasAlignment.Vertical(bias = -1f),
                ),
              horizontalAlignment = Alignment.CenterHorizontally,
            ) {
              Image(
                modifier = Modifier.size(width = 250.dp, height = 200.dp),
                painter = painterResource(id = R.drawable.img_not_found),
                contentDescription = null,
              )
              Text(
                text = "Заявок пока нет",
                color = FootballColors.Text.Tertiary,
                style = BodySemibold,
              )
            }
          }
        }
        is Resource.Error -> {
          // TODO: error state
        }
      }
    }
  }
}
