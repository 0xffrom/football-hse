package goshka133.football.feature_auth.screens.auth.presentation

import goshka133.football.domain_auth.AuthRepository
import goshka133.football.feature_auth.screens.auth.presentation.AuthCommand as Command
import goshka133.football.feature_auth.screens.auth.presentation.AuthEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor

internal class AuthActor
@Inject
constructor(
  private val authRepository: AuthRepository,
) : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> {
    return when (command) {
      is Command.SendOtp ->
        flow { emit(authRepository.sendOtp(command.phoneNumber)) }
          .mapEvents(
            { Internal.SendOtpSuccess },
            Internal::SendOtpError,
          )
      is Command.VerifyCode ->
        flow { emit(authRepository.verifyOtpCode(command.code)) }
          .mapEvents(Internal::VerifyCodeSuccess, Internal::VerifyCodeError)
    }
  }
}
