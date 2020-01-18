package com.rasalexman.tabevents

import com.mincor.kodi.core.*
import com.rasalexman.tabevents.domain.*
import com.rasalexman.tabevents.domain.impl.*

val tabEventsModule = kodiModule {
    bind<IGetTopRatedMoviesFlowUseCase>() with provider {
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