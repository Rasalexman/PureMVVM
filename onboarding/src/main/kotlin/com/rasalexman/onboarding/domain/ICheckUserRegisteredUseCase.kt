package com.rasalexman.onboarding.domain

import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.onboarding.data.SignInEventModel

interface ICheckUserRegisteredUseCase : IUseCase.SingleInOut<SignInEventModel, SResult<Boolean>>