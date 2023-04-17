package andryuh.football.core_network.ext

import retrofit2.HttpException
import retrofit2.Response

fun Response<*>.throwExceptionIfError() {
  if (!isSuccessful) {
    throw HttpException(this)
  }
}
