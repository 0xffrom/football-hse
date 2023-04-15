package andryuh.football.di

import andryuh.football.core_navigation.RouterProvider
import andryuh.football.domain_auth.AuthFeatureApi
import andryuh.football.domain_main.MainFeatureApi

interface RootDependencies {

  val authFeatureApi: AuthFeatureApi
  val mainFeatureApi: MainFeatureApi

  val routerHolder: RouterProvider
}
