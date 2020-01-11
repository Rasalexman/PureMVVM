package com.rasalexman.puremvvm.application

import android.app.Application
import android.content.Context
import com.mincor.kodi.core.*
import com.rasalexman.data.localDataSourceModule
import com.rasalexman.data.remoteDataSourceModule
import com.rasalexman.data.repositoryModule
import com.rasalexman.onboarding.onboardingModule
import com.rasalexman.providers.providersModule
import com.rasalexman.tabevents.tabEventsModule
import com.rasalexman.tabhome.tabHomeModule
import com.rasalexman.tabsearch.tabSearchModule

class MainApplication : Application() {

    private val kodi = kodi {
        bind<Context>() with provider { applicationContext }
        import(providersModule)
        import(localDataSourceModule)
        import(remoteDataSourceModule)
        import(repositoryModule)

        import(onboardingModule)
        import(tabHomeModule)
        import(tabSearchModule)
        import(tabEventsModule)
    }
}