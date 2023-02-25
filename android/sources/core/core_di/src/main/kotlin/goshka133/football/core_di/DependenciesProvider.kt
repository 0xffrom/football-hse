package goshka133.football.core_di

interface DependenciesProvider {

  fun <T> provide(): T
}
