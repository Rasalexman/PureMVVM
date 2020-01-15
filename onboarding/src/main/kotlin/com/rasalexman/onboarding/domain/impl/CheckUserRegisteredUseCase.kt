package com.rasalexman.onboarding.domain.impl

import com.rasalexman.core.common.extensions.errorResult
import com.rasalexman.core.common.extensions.mapIfSuccessSuspend
import com.rasalexman.core.common.extensions.toSuccessResult
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.data.errors.QException
import com.rasalexman.data.repository.IUserRepository
import com.rasalexman.models.inline.toUserEmail
import com.rasalexman.models.inline.toUserName
import com.rasalexman.models.inline.toUserPassword
import com.rasalexman.onboarding.data.SignInEventModel
import com.rasalexman.onboarding.domain.ICheckUserRegisteredUseCase

internal class CheckUserRegisteredUseCase(
    private val userRepository: IUserRepository
) : ICheckUserRegisteredUseCase {

    override suspend fun execute(data: SignInEventModel): SResult<Boolean> {
        val (email, password) = data
        return userRepository.getUser(email).mapIfSuccessSuspend {
            if(this.password == password.value)  userRepository.saveUserData(
                this.name.toUserName(),
                this.email.toUserEmail(),
                this.password.toUserPassword()
            ).run {
                true.toSuccessResult()
            }
            else errorResult(exception = QException.AuthErrors.RepeatedPasswordInvalid)
        }
    }
}