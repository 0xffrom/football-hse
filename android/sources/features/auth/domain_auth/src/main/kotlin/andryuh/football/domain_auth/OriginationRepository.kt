package andryuh.football.domain_auth

import andryuh.football.core_auth.PhoneStorage
import andryuh.football.core_network.ext.throwExceptionIfError
import andryuh.football.domain_profile.ProfileApi
import andryuh.football.domain_profile.dto.RoleType
import javax.inject.Inject

class OriginationRepository
@Inject
constructor(
  private val profileApi: ProfileApi,
  private val phoneStorage: PhoneStorage,
) {

  suspend fun updateProfile(
    fullName: String,
    role: RoleType,
  ) {
    val phoneNumber = phoneStorage.getPhoneRequired()

    val profile = profileApi.getProfile(phoneNumber)

    profileApi
      .updateProfile(
        phoneNumber = phoneNumber,
        profileBody =
          profile.copy(
            fullName = fullName,
            role = role,
          )
      )
      .throwExceptionIfError()
  }
}
