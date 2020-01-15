package com.rasalexman.tabhome

import com.mincor.kodi.core.*
import com.rasalexman.tabhome.domain.genres.IGetGenresUseCase
import com.rasalexman.tabhome.domain.genres.IGetLocalGenresUseCase
import com.rasalexman.tabhome.domain.genres.IGetRemoteGenresUseCase
import com.rasalexman.tabhome.domain.genres.impl.GetGenresUseCase
import com.rasalexman.tabhome.domain.genres.impl.GetLocalGenresUseCase
import com.rasalexman.tabhome.domain.genres.impl.GetRemoteGenresUseCase
import com.rasalexman.tabhome.domain.movies.impl.GetMoviesDataSourceUseCase
import com.rasalexman.tabhome.domain.movies.impl.GetRemoteMoviesByGenreIdUseCase
import com.rasalexman.tabhome.domain.movies.IGetMoviesDataSourceUseCase
import com.rasalexman.tabhome.domain.movies.IGetRemoteMoviesByGenreIdUseCase

val tabHomeModule = kodiModule {
    bind<IGetLocalGenresUseCase>() with provider {
        GetLocalGenresUseCase(
            instance()
        )
    }
    bind<IGetRemoteGenresUseCase>() with provider {
        GetRemoteGenresUseCase(
            instance()
        )
    }
    bind<IGetGenresUseCase>() with provider {
        GetGenresUseCase(
            instance(),
            instance()
        )
    }

    bind<IGetRemoteMoviesByGenreIdUseCase>() with provider {
        GetRemoteMoviesByGenreIdUseCase(
            instance()
        )
    }
    
    bind<IGetMoviesDataSourceUseCase>() with provider {
        GetMoviesDataSourceUseCase(
            instance(),
            instance()
        )
    }
}