package andryuh.football.feature_auth.screens.origination.utils

private const val MinNameLength = 2
private const val MaxNameLength = 40

internal object NameTextFieldValidator {

  fun isCorrect(value: String): Boolean {
    val trimmeredValue = value.trim()

    return (trimmeredValue.length in MinNameLength until MaxNameLength) &&
      !trimmeredValue.any(Char::isDigit) &&
      trimmeredValue.all { it.isLetterOrDigit() || it == ' ' }
  }
}
