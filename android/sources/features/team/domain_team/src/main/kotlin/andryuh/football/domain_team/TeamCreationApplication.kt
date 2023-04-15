package andryuh.football.domain_team

sealed interface TeamCreationApplicationStatus {

  object NotRegistered : TeamCreationApplicationStatus

  data class Registered(val team: Team) : TeamCreationApplicationStatus

  companion object
}

fun TeamCreationApplicationStatus.Companion.mock(): TeamCreationApplicationStatus {
  return TeamCreationApplicationStatus.NotRegistered

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
