package andryuh.football.domain_team.dto

sealed interface TeamCreationApplicationStatus {

  object NotRegistered : TeamCreationApplicationStatus

  data class Registered(val team: Team) : TeamCreationApplicationStatus

}
