package goshka133.football.feature_auth.screens.origination.presentation

import goshka133.football.domain_auth.OriginationRepository
import goshka133.football.feature_auth.screens.origination.presentation.OriginationCommand as Command
import goshka133.football.feature_auth.screens.origination.presentation.OriginationEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor

internal class OriginationActor
@Inject
constructor(
  val originationRepository: OriginationRepository,
) : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> {
    return when (command) {
      is Command.UpdateProfile ->
        flow {
            emit(
              originationRepository.updateProfile(
                fullName = command.fullName,
                role = command.role,
              )
            )
          }
          .mapEvents(
            { Internal.UpdateProfileSuccess },
            Internal::UpdateProfileError,
          )
    }
  }
}
