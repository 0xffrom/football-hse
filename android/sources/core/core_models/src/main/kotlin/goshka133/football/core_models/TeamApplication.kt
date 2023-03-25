package goshka133.football.core_models

sealed interface TeamApplication {

  object NotRegistered : TeamApplication

  data class Registered(val team: Team) : TeamApplication

  companion object
}

data class Team(
  val id: String,
  val name: String,
  val status: TeamStatus,
)

enum class TeamStatus {
  OnValidation,
  Verified,
  Declined,
}

fun TeamApplication.Companion.mock(): TeamApplication {
  return TeamApplication.NotRegistered

  //  return if (Random.nextBoolean()) {
  //    TeamApplication.NotRegistered
  //  } else {
  //    TeamApplication.Registered(
  //      team =
  //        Team(
  //          id = "1",
  //          name = "ФК Вышка",
  //          status = TeamStatus.values().random(),
  //        )
  //    )
  //  }
}
