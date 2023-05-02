package andryuh.football.feature_chat.models

import android.os.Parcelable
import java.time.LocalDateTime
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleMessage(
  val content: String,
  val date: LocalDateTime,
) : Parcelable
