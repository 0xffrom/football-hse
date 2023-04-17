package andryuh.football.core_kotlin

sealed interface Resource<out T : Any> {

  object Loading : Resource<Nothing>

  data class Error(val error: Throwable) : Resource<Nothing>

  data class Data<out T : Any>(val data: T) : Resource<T> {

    override val value: T
      get() = data
  }

  val value: T?
    get() = (this as? Data<T>)?.data
}
