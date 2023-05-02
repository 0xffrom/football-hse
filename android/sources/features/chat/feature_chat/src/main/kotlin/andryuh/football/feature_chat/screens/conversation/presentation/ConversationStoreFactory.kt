package andryuh.football.feature_chat.screens.conversation.presentation

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.domain_chat.dto.Conversation
import andryuh.football.feature_chat.screens.conversation.presentation.ConversationEffect as Effect
import andryuh.football.feature_chat.screens.conversation.presentation.ConversationEvent as Event
import andryuh.football.feature_chat.screens.conversation.presentation.ConversationState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class ConversationStoreFactory
@Inject
constructor(
  private val actor: ConversationActor,
) : StoreFactory {

  fun create(conversation: Conversation): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState = State(conversation = conversation),
      reducer = ConversationReducer,
      actor = actor,
    )
}
