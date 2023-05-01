package andryuh.football.domain_chat

import andryuh.football.domain_chat.dto.ConversationResponseBody
import andryuh.football.domain_chat.dto.CreateConversationRequestBody
import andryuh.football.domain_chat.dto.Message
import andryuh.football.domain_chat.dto.SendMessageRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatApi {

  @GET("Chats/user/{phoneNumber}")
  suspend fun getConversations(
    @Path("phoneNumber") phoneNumber: String
  ): List<ConversationResponseBody>

  @POST("Chats")
  suspend fun createConversation(@Body body: CreateConversationRequestBody): Response<Unit>

  @GET("Messages/{phone1}/{phone2}/0")
  suspend fun getMessages(
    @Path("phone1") senderPhoneNumber: String,
    @Path("phone2") receiverPhoneNumber: String,
  ): List<Message>

  @POST("Messages") suspend fun sendMessage(@Body body: SendMessageRequestBody): Response<Unit>
}
