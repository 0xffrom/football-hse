package andryuh.football.feature_main.di

import andryuh.football.domain_chat.ChatFeatureApi
import andryuh.football.domain_profile.ProfileFeatureApi
import andryuh.football.domain_search.SearchFeatureApi

interface MainFeatureDependencies {

  val searchFeatureApi: SearchFeatureApi
  val chatFeatureApi: ChatFeatureApi
  val profileFeatureApi: ProfileFeatureApi
}
