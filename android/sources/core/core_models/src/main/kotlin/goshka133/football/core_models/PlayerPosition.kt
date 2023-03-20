package goshka133.football.core_models

enum class PlayerPosition {
  CentralForward,
  LeftForward,
  RightForward,
  CentralAttackingMidfielder,
  DefensiveMidfielder,
  LeftMidfielder,
  RightMidfielder,
  CentralDefender,
  LeftDefender,
  RightDefender,
  Goalkeeper,
}

fun List<PlayerPosition>.mapToTitle() = map { playerPosition -> playerPosition.toTitle() }

fun PlayerPosition.toTitle(): String {
  return when (this) {
    PlayerPosition.CentralForward -> "Центральный форвард"
    PlayerPosition.LeftForward -> "Левый форвард"
    PlayerPosition.RightForward -> "Правый форвард"
    PlayerPosition.CentralAttackingMidfielder -> "Центральный атакующий полузащитник"
    PlayerPosition.DefensiveMidfielder -> "Опорный полузащитник"
    PlayerPosition.LeftMidfielder -> "Левый полузащитник"
    PlayerPosition.RightMidfielder -> "Правый полузащиник"
    PlayerPosition.CentralDefender -> "Центральный защитник"
    PlayerPosition.LeftDefender -> "Левый защитник"
    PlayerPosition.RightDefender -> "Правый защитник"
    PlayerPosition.Goalkeeper -> "Голкипер"
  }
}
