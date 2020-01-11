package com.rasalexman.onboarding

import com.mincor.kodi.core.*
import com.rasalexman.onboarding.domain.CheckUserRegisteredUseCase
import com.rasalexman.onboarding.domain.RegisterUserUseCase

val onboardingModule = kodiModule {
    bind<CheckUserRegisteredUseCase>() with provider { CheckUserRegisteredUseCase(instance()) }
    bind<RegisterUserUseCase>() with provider { RegisterUserUseCase(instance()) }
}