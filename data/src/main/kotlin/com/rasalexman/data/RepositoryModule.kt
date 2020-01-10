package com.rasalexman.data

import com.mincor.kodi.core.*
import com.rasalexman.data.repository.*

val repositoryModule = kodiModule {
    bind<IUserRepository>() with single {
        UserRepository(
            instance()
        )
    }

    bind<IGenresRepository>() with single {
        GenresRepository(
            instance(), instance()
        )
    }

    bind<IMoviesRepository>() with single {
        MoviesRepository(
            instance(), instance()
        )
    }
}