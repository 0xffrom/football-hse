package goshka133.football.di

import goshka133.football.core_navigation.RouterProvider
import goshka133.football.domain_auth.AuthFeatureApi
import goshka133.football.domain_main.MainFeatureApi

interface RootDependencies {

  val authFeatureApi: AuthFeatureApi
  val mainFeatureApi: MainFeatureApi

  val routerHolder: RouterProvider
}
