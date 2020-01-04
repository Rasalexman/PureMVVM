package com.rasalexman.puremvvm.modules

import com.mincor.kodi.core.*
import com.rasalexman.providers.data.repository.IUserRepository
import com.rasalexman.puremvvm.data.repository.UserRepository

val repositoryModule = kodiModule {
    bind<IUserRepository>() with single {
        UserRepository(
            instance()
        )
    }
}