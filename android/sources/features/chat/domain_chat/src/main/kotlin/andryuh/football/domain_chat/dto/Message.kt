package andryuh.football.domain_chat.dto

import android.os.Parcelable
import kotlinx.datetime.LocalDateTime
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
@Parcelize
data class Message(
  val id: String,
  @SerialName("sendTime") val sendTimeRaw: String? = null,
  @SerialName("text") val content: String,
  val isRead: Boolean,
  @SerialName("sender") val senderPhoneNumber: String,
  @SerialName("receiver") val receiverPhoneNumber: String,
) : Parcelable {

  @IgnoredOnParcel
  @Transient
  val sendTime: LocalDateTime? =
    runCatching<LocalDateTime?> { sendTimeRaw?.let(Json::decodeFromString) }.getOrNull()
}

@Serializable
data class SendMessageRequestBody(
  @SerialName("sender") val senderPhoneNumber: String,
  @SerialName("receiver") val receiverPhoneNumber: String,
  @SerialName("text") val content: String,
  val sendTime: @RawValue LocalDateTime,
)
