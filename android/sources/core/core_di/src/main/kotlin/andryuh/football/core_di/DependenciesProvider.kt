package andryuh.football.core_di

interface DependenciesProvider {

  fun <T> provide(): T
}
