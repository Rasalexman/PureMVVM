package com.rasalexman.onboarding.domain

import com.rasalexman.core.common.extensions.errorResult
import com.rasalexman.core.common.extensions.mapIfSuccessSuspend
import com.rasalexman.core.common.extensions.toSuccessResult
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.data.errors.QException
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.data.repository.IUserRepository
import com.rasalexman.onboarding.data.SignInEventModel

class CheckUserRegisteredUseCase(
    private val userRepository: IUserRepository
) : IUseCase.SingleInOut<SignInEventModel, SResult<Boolean>> {

    override suspend fun execute(data: SignInEventModel): SResult<Boolean> {
        val (email, password) = data
        return userRepository.getUser(email).mapIfSuccessSuspend {
            if(this.password == password.value)  true.toSuccessResult()
            else errorResult(exception = QException.AuthErrors.RepeatedPasswordInvalid)
        }
    }
}