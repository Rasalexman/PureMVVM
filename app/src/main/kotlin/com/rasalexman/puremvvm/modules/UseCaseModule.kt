package com.rasalexman.puremvvm.modules

import com.mincor.kodi.core.import
import com.mincor.kodi.core.kodiModule
import com.rasalexman.onboarding.onboardingUseCaseModule
import com.rasalexman.tabhome.tabHomeUseCaseModule

val userCaseModule = kodiModule {
    import(onboardingUseCaseModule)
    import(tabHomeUseCaseModule)
}