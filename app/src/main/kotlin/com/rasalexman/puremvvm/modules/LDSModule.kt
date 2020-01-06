package com.rasalexman.puremvvm.modules

import com.mincor.kodi.core.*
import com.rasalexman.providers.data.source.local.IGenresLocalDataSource
import com.rasalexman.providers.data.source.local.IMoviesLocalDataSource
import com.rasalexman.providers.data.source.local.IUserLocalDataSource
import com.rasalexman.puremvvm.data.source.local.GenresLocalDataSource
import com.rasalexman.puremvvm.data.source.local.MoviesLocalDataSource
import com.rasalexman.puremvvm.data.source.local.UserLocalDataSource

val localDataSourceModule = kodiModule {
    bind<IUserLocalDataSource>() with single {
        UserLocalDataSource(
            instance(), instance()
        )
    }

    bind<IGenresLocalDataSource>() with single {
        GenresLocalDataSource(
            instance()
        )
    }

    bind<IMoviesLocalDataSource>() with single {
        MoviesLocalDataSource(
            instance()
        )
    }
}