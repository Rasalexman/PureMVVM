package com.rasalexman.puremvvm.application

import android.app.Application
import android.content.Context
import com.mincor.kodi.core.*
import com.rasalexman.providers.providersModule
import com.rasalexman.puremvvm.modules.localDataSourceModule
import com.rasalexman.puremvvm.modules.repositoryModule
import com.rasalexman.puremvvm.modules.userCaseModule

class MainApplication : Application() {

    private val kodi = kodi {
        bind<Context>() with provider { applicationContext }
        import(providersModule)
        import(localDataSourceModule)
        import(repositoryModule)
        import(userCaseModule)
    }

    override fun onCreate() {
        super.onCreate()
        kodi
    }
}