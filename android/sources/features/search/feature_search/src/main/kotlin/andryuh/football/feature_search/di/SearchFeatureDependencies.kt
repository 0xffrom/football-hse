package andryuh.football.feature_search.di

import andryuh.football.domain_profile.ProfileFeatureApi
import andryuh.football.domain_team.TeamFeatureApi

interface SearchFeatureDependencies {

    val teamFeatureApi: TeamFeatureApi
    val profileFeatureApi: ProfileFeatureApi
}
