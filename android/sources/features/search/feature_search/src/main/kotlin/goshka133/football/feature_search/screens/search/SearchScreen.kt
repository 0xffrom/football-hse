package goshka133.football.feature_search.screens.search

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import goshka133.football.core_elmslie.rememberStore
import goshka133.football.feature_search.screens.search.presentation.SearchStoreFactory
import goshka133.football.ui_kit.BaseScreen
import kotlinx.parcelize.Parcelize

@Parcelize
internal class SearchScreen : BaseScreen() {

  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = SearchStoreFactory::class.java,
        storeProvider = { storeFactory, _ -> storeFactory.create() },
      )
    val state by store.states().collectAsState(store.currentState)

    LaunchedEffect(key1 = store) {
      store.effects().collect { effect ->
        when (effect) {
          else -> Unit
        }
      }
    }
    Scaffold(
      topBar = {},
    ) { contentPadding ->
    }
  }
}
