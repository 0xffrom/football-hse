package goshka133.football.domain_profile.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
  val fullName: String,
  val footballExperience: String,
  val tournamentsExperience: String,
  val contactInfo: String,
  val about: String,
  val imageUrl: String?,
  val isCaptain: Boolean,
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
  )
}
