package com.rasalexman.puremvvm.modules

import com.mincor.kodi.core.*
import com.rasalexman.providers.data.source.local.IUserLocalDataSource
import com.rasalexman.puremvvm.data.source.local.UserLocalDataSource

val localDataSourceModule = kodiModule {
    bind<IUserLocalDataSource>() with single {
        UserLocalDataSource(
            instance(), instance()
        )
    }
}