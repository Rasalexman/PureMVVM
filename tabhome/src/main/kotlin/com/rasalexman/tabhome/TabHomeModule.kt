package com.rasalexman.tabhome

import com.mincor.kodi.core.*
import com.rasalexman.tabhome.domain.genres.GetGenresUseCase
import com.rasalexman.tabhome.domain.genres.GetLocalGenresUseCase
import com.rasalexman.tabhome.domain.genres.GetRemoteGenresUseCase
import com.rasalexman.tabhome.domain.movies.GetLocalMoviesDataSourceUseCase
import com.rasalexman.tabhome.domain.movies.GetRemoteMoviesByGenreIdUseCase

val tabHomeUseCaseModule = kodiModule {
    bind<GetLocalGenresUseCase>() with provider {
        GetLocalGenresUseCase(
            instance()
        )
    }
    bind<GetRemoteGenresUseCase>() with provider {
        GetRemoteGenresUseCase(
            instance()
        )
    }
    bind<GetGenresUseCase>() with provider {
        GetGenresUseCase(
            instance(),
            instance()
        )
    }

    bind<GetRemoteMoviesByGenreIdUseCase>() with provider {
        GetRemoteMoviesByGenreIdUseCase(
            instance()
        )
    }
    bind<GetLocalMoviesDataSourceUseCase>() with provider {
        GetLocalMoviesDataSourceUseCase(
            instance()
        )
    }
}