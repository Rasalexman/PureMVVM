package com.rasalexman.puremvvm.modules

import com.mincor.kodi.core.*
import com.rasalexman.onboarding.onboardingUseCaseModule
import com.rasalexman.puremvvm.domain.GetSearchDataSource
import com.rasalexman.tabhome.tabHomeUseCaseModule

val userCaseModule = kodiModule {
    import(onboardingUseCaseModule)
    import(tabHomeUseCaseModule)

    bind<GetSearchDataSource>() with provider { GetSearchDataSource(instance()) }
}