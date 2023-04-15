package andryuh.football.feature_profile.screens.edit_profile.presentation

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.domain_profile.dto.Profile
import andryuh.football.feature_profile.screens.edit_profile.presentation.EditProfileEffect as Effect
import andryuh.football.feature_profile.screens.edit_profile.presentation.EditProfileEvent as Event
import andryuh.football.feature_profile.screens.edit_profile.presentation.EditProfileState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class EditProfileStoreFactory
@Inject
constructor(
  private val actor: EditProfileActor,
) : StoreFactory {

  fun create(
    profile: Profile,
  ): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState = State(profile = profile),
      reducer = EditProfileReducer,
      actor = actor,
    )
}
