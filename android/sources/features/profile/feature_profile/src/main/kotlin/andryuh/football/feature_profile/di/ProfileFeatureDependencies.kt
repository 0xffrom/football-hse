package andryuh.football.feature_profile.di

import andryuh.football.domain_auth.AuthFeatureApi
import andryuh.football.domain_chat.ChatFeatureApi
import andryuh.football.domain_main.BottomTabController
import andryuh.football.domain_profile.ProfileFeatureApi
import andryuh.football.domain_team.TeamFeatureApi

interface ProfileFeatureDependencies {

    val teamFeatureApi: TeamFeatureApi
    val profileFeatureApi: ProfileFeatureApi
    val authFeatureApi: AuthFeatureApi

    val bottomTabController: BottomTabController
}
