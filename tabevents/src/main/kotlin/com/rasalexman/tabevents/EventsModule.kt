package com.rasalexman.tabevents

import com.mincor.kodi.core.*
import com.rasalexman.tabevents.domain.*
import com.rasalexman.tabevents.domain.impl.GetPopularMoviesPageCountUseCase
import com.rasalexman.tabevents.domain.impl.GetPopularMoviesUseCase
import com.rasalexman.tabevents.domain.impl.GetTopRatedMoviesUseCase
import com.rasalexman.tabevents.domain.impl.GetUpcomingMoviesUseCase
import com.rasalexman.tabevents.domain.impl.LoadPopularMoviesUseCase

val tabEventsModule = kodiModule {
    bind<IGetTopRatedMoviesUseCase>() with provider {
        GetTopRatedMoviesUseCase(
            instance()
        )
    }
    bind<IGetUpcomingMoviesUseCase>() with provider {
        GetUpcomingMoviesUseCase(
            instance()
        )
    }
    bind<ILoadPopularMoviesUseCase>() with provider {
        LoadPopularMoviesUseCase(
            instance()
        )
    }
    bind<IGetPopularMoviesPageCountUseCase>() with provider {
        GetPopularMoviesPageCountUseCase(
            instance()
        )
    }
    bind<IGetPopularMoviesUseCase>() with provider {
        GetPopularMoviesUseCase(
            instance(),
            instance(),
            instance()
        )
    }
}