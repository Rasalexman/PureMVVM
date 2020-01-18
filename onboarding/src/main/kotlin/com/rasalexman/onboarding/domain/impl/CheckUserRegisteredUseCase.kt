package com.rasalexman.onboarding.domain.impl

import com.rasalexman.core.common.extensions.errorResult
import com.rasalexman.core.common.extensions.mapIfSuccessSuspend
import com.rasalexman.core.common.extensions.toSuccessResult
import com.rasalexman.core.common.typealiases.AnyResult
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

    override suspend fun execute(data: SignInEventModel): AnyResult {
        val (email, password) = data
        return userRepository.getUser(email).mapIfSuccessSuspend {
            this.takeIf { it.password == password.value }?.run {
                userRepository.saveUserData(
                    this.name.toUserName(),
                    this.email.toUserEmail(),
                    this.password.toUserPassword()
                )
                toSuccessResult()
            } ?: errorResult(exception = QException.AuthErrors.RepeatedPasswordInvalid)
        }
    }
}