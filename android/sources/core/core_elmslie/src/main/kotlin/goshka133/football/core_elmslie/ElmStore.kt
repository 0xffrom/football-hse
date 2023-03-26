package goshka133.football.core_elmslie

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import goshka133.football.core_di.rememberDependencies
import goshka133.football.core_elmslie.di.ElmDependencies
import vivid.money.elmslie.android.RetainedElmStore
import vivid.money.elmslie.android.RetainedElmStoreFactory
import vivid.money.elmslie.core.store.Store

@Composable
fun <SF : StoreFactory, Event : Any, Effect : Any, State : Any> rememberStore(
  storeFactoryClass: Class<SF>,
  storeProvider: (SF, SavedStateHandle) -> Store<Event, Effect, State>,
): Store<Event, Effect, State> {
  val savedStateRegistryOwner = LocalSavedStateRegistryOwner.current
  val vmStoreOwner = LocalViewModelStoreOwner.current!!

  val elmDependencies: ElmDependencies = rememberDependencies()
  val storeFactory = remember { elmDependencies.storeFactoryProvider.get(storeFactoryClass) }

  return remember(
    key1 = vmStoreOwner,
    key2 = savedStateRegistryOwner,
  ) {
    val factory =
      RetainedElmStoreFactory(
        stateRegistryOwner = savedStateRegistryOwner,
        storeFactory = { savedStateHandle -> storeProvider.invoke(storeFactory, savedStateHandle) },
        defaultArgs = Bundle(),
      )
    val provider = ViewModelProvider(vmStoreOwner, factory)

    @Suppress("UNCHECKED_CAST")
    provider[storeFactoryClass.name, RetainedElmStore::class.java].store
      as Store<Event, Effect, State>
  }
}
