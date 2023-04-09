package goshka133.football.domain_profile.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Profile(
  @SerialName("name") val fullName: String?,
  val footballExperience: String?,
  @SerialName("tournamentExperience") val tournamentsExperience: String?,
  @SerialName("contact") val contactInfo: String?,
  val about: String?,
  @SerialName("photo") val imageUrl: String?,
  val isCaptain: Boolean,
  val isRegistered: Boolean,
  val phoneNumber: String,
  @SerialName("hseRole") val role: RoleType,
) : Parcelable {

  companion object
}

fun Profile.Companion.mock(): Profile {
  return Profile(
    fullName = "Иванов Василий Петрович",
    footballExperience = "Играл в Лиге чемпионов в 2020 году, закончил НИУ ВШЭ",
    tournamentsExperience = "Успешно завершил зимний турнир \"Москва слезам не верит\"",
    about =
      "Я люблю играть футбол и компьютерные игры, особенно в игру FIFA 2020. " +
        "Увлекаюсь горнолыжным видом спорта, хожу в бассейн.",
    imageUrl = null,
    contactInfo = "Telegram: @best_football_player",
    isCaptain = true,
    role = RoleType.values().random(),
    isRegistered = false,
    phoneNumber = "",
  )
}
