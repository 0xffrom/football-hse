package andryuh.football.core_models

enum class Tournament {
  AutumnCup,
  FutsalYouthLeague,
  FutsalSecondLeague,
  FutsalFirstLeague,
  FutsalPremierLeague,
  YouthLeague,
  SecondLeague,
  FirstLeague,
  MajorLeague,
  SummerCup,
}

fun List<Tournament>.joinTitleToString(separator: String = ", "): String {
  return mapIndexed { index, tournament ->
      tournament.toTitle().run { if (index != 0) lowercase() else this }
    }
    .joinToString(separator = separator)
}

fun Tournament.toTitle(): String {
  return when (this) {
    Tournament.AutumnCup -> "Осенний кубок"
    Tournament.SummerCup -> "Летний кубок"
    Tournament.FirstLeague -> "Первая лига"
    Tournament.SecondLeague -> "Вторая лига"
    Tournament.MajorLeague -> "Высшая лига"
    Tournament.YouthLeague -> "Молодежная лига"
    Tournament.FutsalFirstLeague -> "Футзал первая лига"
    Tournament.FutsalSecondLeague -> "Футзал вторая лига"
    Tournament.FutsalPremierLeague -> "Футзал премьерная лига"
    Tournament.FutsalYouthLeague -> "Футзал молодежная лига"
  }
}
