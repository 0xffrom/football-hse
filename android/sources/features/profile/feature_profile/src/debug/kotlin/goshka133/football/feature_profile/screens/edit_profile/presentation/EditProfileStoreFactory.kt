package goshka133.football.feature_profile.screens.edit_profile.presentation

import goshka133.football.core_elmslie.StoreFactory
import goshka133.football.domain_profile.dto.Profile
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileEffect as Effect
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileEvent as Event
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileState as State
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
