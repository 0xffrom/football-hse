package andryuh.football.core_kotlin

fun String?.nullIfBlank() = takeUnless(String?::isNullOrBlank)
