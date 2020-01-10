package com.rasalexman.data

import com.mincor.kodi.core.*
import com.rasalexman.data.source.local.*

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