package com.rasalexman.onboarding.domain

import com.rasalexman.core.common.extensions.errorResult
import com.rasalexman.core.common.extensions.toSuccessResult
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.data.errors.QException
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.onboarding.data.SignInEventModel
import com.rasalexman.providers.data.repository.IUserRepository

class CheckUserRegisteredUseCase(
    private val userRepository: IUserRepository
) : IUseCase.SingleInOut<SignInEventModel, SResult<Boolean>> {

    override suspend fun execute(data: SignInEventModel): SResult<Boolean> {
        val (email, password) = data
        return when (val result = userRepository.getUser(email)) {
            is SResult.Success -> {
                if (result.data.password == password.value) {
                    true.toSuccessResult()
                } else {
                    errorResult(exception = QException.AuthErrors.RepeatedPasswordInvalid)
                }
            }
            is SResult.Empty -> errorResult(exception = QException.AuthErrors.UserNullError())
            else -> errorResult(exception = QException.AuthErrors.SignInFailedError())
        }
    }
}