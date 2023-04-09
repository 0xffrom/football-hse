package goshka133.football.core_kotlin

fun String?.nullIfBlank() = takeUnless(String?::isNullOrBlank)
