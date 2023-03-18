package goshka133.football.feature_main.di

import goshka133.football.domain_chat.ChatFeatureApi
import goshka133.football.domain_profile.ProfileFeatureApi
import goshka133.football.domain_search.SearchFeatureApi

interface MainFeatureDependencies {

  val searchFeatureApi: SearchFeatureApi
  val chatFeatureApi: ChatFeatureApi
  val profileFeatureApi: ProfileFeatureApi
}
