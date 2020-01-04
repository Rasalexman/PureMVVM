package com.rasalexman.puremvvm.modules

import com.mincor.kodi.core.import
import com.mincor.kodi.core.kodiModule
import com.rasalexman.onboarding.modules.onboardingUseCaseModule

val userCaseModule = kodiModule {
    import(onboardingUseCaseModule)
}