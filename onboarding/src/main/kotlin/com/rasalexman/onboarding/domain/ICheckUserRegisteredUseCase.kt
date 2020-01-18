package com.rasalexman.onboarding.domain

import com.rasalexman.core.common.typealiases.AnyResult
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.onboarding.data.SignInEventModel

interface ICheckUserRegisteredUseCase : IUseCase.SingleInOut<SignInEventModel, AnyResult>