package goshka133.football.di

import goshka133.football.domain_auth.AuthFeatureApi
import goshka133.football.domain_main.MainFeatureApi
import goshka133.football.feature_auth.di.AuthDependencies

interface RootDependencies : AuthDependencies {

  val authFeatureApi: AuthFeatureApi
  override val mainFeatureApi: MainFeatureApi
}
