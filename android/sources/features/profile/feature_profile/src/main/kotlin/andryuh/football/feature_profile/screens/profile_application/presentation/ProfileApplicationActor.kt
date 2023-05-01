package andryuh.football.feature_profile.screens.profile_application.presentation

import andryuh.football.domain_chat.ChatRepository
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationCommand as Command
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor

internal class ProfileApplicationActor
@Inject
constructor(
  private val chatRepository: ChatRepository,
) : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> {
    return when (command) {
      is Command.CreateChat ->
        flow { emit(chatRepository.createConversation(command.phoneNumber)) }
          .mapEvents({ Internal.CreateChatSuccess }, Internal::CreateChatError)
    }
  }
}
