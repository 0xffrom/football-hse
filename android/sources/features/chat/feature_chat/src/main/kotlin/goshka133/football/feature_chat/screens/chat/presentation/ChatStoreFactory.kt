package goshka133.football.feature_chat.screens.chat.presentation

import goshka133.football.core_elmslie.StoreFactory
import goshka133.football.feature_chat.screens.chat.presentation.ChatEffect as Effect
import goshka133.football.feature_chat.screens.chat.presentation.ChatEvent as Event
import goshka133.football.feature_chat.screens.chat.presentation.ChatState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class ChatStoreFactory
@Inject
constructor(
  private val actor: ChatActor,
) : StoreFactory {

  fun create(): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState = State(),
      reducer = ChatReducer,
      actor = actor,
    )
}
