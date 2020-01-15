package com.rasalexman.onboarding

import com.mincor.kodi.core.*
import com.rasalexman.onboarding.domain.impl.CheckUserRegisteredUseCase
import com.rasalexman.onboarding.domain.ICheckUserRegisteredUseCase
import com.rasalexman.onboarding.domain.IRegisterUserUseCase
import com.rasalexman.onboarding.domain.impl.RegisterUserUseCase

val onboardingModule = kodiModule {
    bind<ICheckUserRegisteredUseCase>() with provider {
        CheckUserRegisteredUseCase(
            instance()
        )
    }
    bind<IRegisterUserUseCase>() with provider {
        RegisterUserUseCase(
            instance()
        )
    }
}