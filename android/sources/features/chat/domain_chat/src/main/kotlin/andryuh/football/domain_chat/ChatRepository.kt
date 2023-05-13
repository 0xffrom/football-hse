package andryuh.football.domain_chat

import andryuh.football.core_auth.PhoneStorage
import andryuh.football.core_network.ext.throwExceptionIfError
import andryuh.football.domain_chat.dto.Conversation
import andryuh.football.domain_chat.dto.CreateConversationRequestBody
import andryuh.football.domain_chat.dto.Message
import andryuh.football.domain_chat.dto.SendMessageRequestBody
import andryuh.football.domain_profile.ProfileApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import timber.log.Timber

@Singleton
class ChatRepository
@Inject
constructor(
  private val phoneNumberStorage: PhoneStorage,
  private val chatApi: ChatApi,
  private val profileApi: ProfileApi,
) {

  private val conversationsCache = MutableStateFlow<List<Conversation>?>(null)
  private val messagesCacheMap = HashMap<String, MutableStateFlow<List<Message>?>>()

  fun observeConversations(): Flow<List<Conversation>> {
    CoroutineScope(Dispatchers.IO).launch {
      delay(500L)
      updateConversations()
    }

    polling { updateConversations() }

    return conversationsCache.filterNotNull()
  }

  suspend fun forceUpdateConversations() {
    updateConversations()
  }

  suspend fun createConversation(receiverPhoneNumber: String) {
    val senderPhoneNumber = phoneNumberStorage.getPhoneRequired()

    val isChatExists =
      runCatching { chatApi.getConversations(senderPhoneNumber) }
        .onFailure(Timber::e)
        .getOrNull()
        .orEmpty()
        .any { conversation ->
          conversation.senderPhoneNumber == receiverPhoneNumber ||
            conversation.receiverPhoneNumber == receiverPhoneNumber
        }

    if (isChatExists) return

    val profiles = profileApi.getAllProfile()

    val senderProfile = profiles.first { it.phoneNumber == senderPhoneNumber }
    val receiverProfile = profiles.first { it.phoneNumber == receiverPhoneNumber }

    chatApi
      .createConversation(
        body =
          CreateConversationRequestBody(
            senderPhoneNumber = senderProfile.phoneNumber,
            senderName = senderProfile.fullName,
            senderPhotoUrl = senderProfile.imageUrl,
            receiverPhoneNumber = receiverProfile.phoneNumber,
            receiverName = receiverProfile.fullName,
            receiverPhotoUrl = receiverProfile.imageUrl,
          )
      )
      .throwExceptionIfError()

    updateConversations()
  }

  fun observeMessages(conversation: Conversation): Flow<List<Message>> {
    val cache = messagesCacheMap.getOrPut(key = conversation.phoneNumber) { MutableStateFlow(null) }

    CoroutineScope(Dispatchers.IO).launch { updateMessages(cache, conversation.phoneNumber) }

    polling { updateMessages(cache, conversation.phoneNumber) }

    return cache.filterNotNull()
  }

  suspend fun sendMessage(conversation: Conversation, messageContent: String): List<Message> {
    val userPhoneNumber = phoneNumberStorage.getPhoneRequired()

    val cache = messagesCacheMap.getOrPut(key = conversation.phoneNumber) { MutableStateFlow(null) }

    chatApi.sendMessage(
      SendMessageRequestBody(
        senderPhoneNumber = userPhoneNumber,
        receiverPhoneNumber = conversation.phoneNumber,
        content = messageContent,
        sendTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
      )
    )

    updateMessages(cache, conversation.phoneNumber)

    return cache.value.orEmpty()
  }

  private suspend fun updateMessages(
    cache: MutableStateFlow<List<Message>?>,
    receiverPhoneNumber: String
  ) {
    phoneNumberStorage.getPhone()?.takeIf(String::isNotBlank)?.let { userPhoneNumber ->
      cache.emit(chatApi.getMessages(userPhoneNumber, receiverPhoneNumber))
    }
  }

  private suspend fun updateConversations() {
    val userPhoneNumber = phoneNumberStorage.getPhoneRequired()

    userPhoneNumber.takeIf(String::isNotBlank)?.let {
      conversationsCache.emit(
        chatApi
          .getConversations(userPhoneNumber)
          .filter { body ->
            body.senderPhoneNumber != "88888888888" && body.receiverPhoneNumber != "88888888888"
          }
          .map { response ->
            val (name, phoneNumber, photoUrl) =
              if (userPhoneNumber == response.receiverPhoneNumber) {
                Triple(response.senderName, response.senderPhoneNumber, response.senderPhotoUrl)
              } else {
                Triple(
                  response.receiverName,
                  response.receiverPhoneNumber,
                  response.receiverPhotoUrl
                )
              }

            Conversation(
              id = response.id,
              lastMessage = response.lastMessage,
              name = name.orEmpty(),
              phoneNumber = phoneNumber,
              photoUrl = photoUrl,
            )
          }
          .distinctBy(Conversation::phoneNumber)
          .filter { conversation -> conversation.name.isNotBlank() }
      )
    }
  }

  private fun polling(
    delay: Long = 3000L,
    onPoll: suspend () -> Unit,
  ) {
    CoroutineScope(Dispatchers.IO).launch {
      flow {
          while (true) {
            delay(3000L)
            emit(Unit)
          }
        }
        .collect { onPoll.invoke() }
    }
  }
}
