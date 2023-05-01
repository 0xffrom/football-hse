package andryuh.football.domain_chat.dto

import android.os.Parcelable
import kotlinx.datetime.LocalDateTime
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Message(
  val id: String,
  val sendTime: @RawValue LocalDateTime,
  @SerialName("text") val content: String,
  val isRead: Boolean,
  @SerialName("sender") val senderPhoneNumber: String,
  @SerialName("receiver") val receiverPhoneNumber: String,
) : Parcelable

@Serializable
data class SendMessageRequestBody(
  @SerialName("sender") val senderPhoneNumber: String,
  @SerialName("receiver") val receiverPhoneNumber: String,
  @SerialName("text") val content: String,
)
