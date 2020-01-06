package com.rasalexman.onboarding.domain

import com.rasalexman.core.common.extensions.alertResult
import com.rasalexman.core.common.extensions.errorResult
import com.rasalexman.core.common.extensions.toSuccessResult
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.data.errors.QException
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.onboarding.data.SignUpEventModel
import com.rasalexman.providers.data.repository.IUserRepository

class RegisterUserUseCase(
    private val userRepository: IUserRepository
) : IUseCase.SingleInOut<SignUpEventModel, SResult<Boolean>> {
    override suspend fun execute(data: SignUpEventModel): SResult<Boolean> {
        return when (userRepository.getUser(data.email)) {
            is SResult.Success -> alertResult(exception = QException.AuthErrors.UserExistError)
            is SResult.ErrorResult -> userRepository.saveUserData(
                data.name,
                data.email,
                data.password
            ).run {
                true.toSuccessResult()
            }
            else -> errorResult(exception = QException.AuthErrors.ResultError())
        }
    }
}