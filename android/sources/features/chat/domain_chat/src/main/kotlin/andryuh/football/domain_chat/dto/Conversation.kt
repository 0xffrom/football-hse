package andryuh.football.domain_chat.dto

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Immutable
data class Conversation(
  val id: String,
  val lastMessage: Message?,
  val name: String,
  val phoneNumber: String,
  val photoUrl: String?,
) : Parcelable

@Serializable
data class ConversationResponseBody(
  val id: String,
  val lastMessage: Message?,
  @SerialName("phoneNumber1") val senderPhoneNumber: String,
  @SerialName("name1") val senderName: String?,
  @SerialName("photo1") val senderPhotoUrl: String?,
  @SerialName("phoneNumber2") val receiverPhoneNumber: String,
  @SerialName("name2") val receiverName: String?,
  @SerialName("photo2") val receiverPhotoUrl: String?,
)

@Serializable
data class CreateConversationRequestBody(
  @SerialName("phoneNumber1") val senderPhoneNumber: String,
  @SerialName("name1") val senderName: String?,
  @SerialName("photo1") val senderPhotoUrl: String?,
  @SerialName("phoneNumber2") val receiverPhoneNumber: String?,
  @SerialName("name2") val receiverName: String?,
  @SerialName("photo2") val receiverPhotoUrl: String?,
)
