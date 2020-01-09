package com.rasalexman.puremvvm.modules

import com.mincor.kodi.core.*
import com.rasalexman.onboarding.onboardingUseCaseModule
import com.rasalexman.puremvvm.domain.GetSearchDataSource
import com.rasalexman.tabevents.tabEventsModule
import com.rasalexman.tabhome.tabHomeUseCaseModule

val userCaseModule = kodiModule {
    import(onboardingUseCaseModule)
    import(tabHomeUseCaseModule)
    import(tabEventsModule)

    bind<GetSearchDataSource>() with provider { GetSearchDataSource(instance()) }
}