package com.rasalexman.tabevents

import com.mincor.kodi.core.*
import com.rasalexman.tabevents.domain.*

val tabEventsModule = kodiModule {
    bind<GetTopRatedMoviesUseCase>() with provider { GetTopRatedMoviesUseCase(instance()) }
    bind<GetUpcomingMoviesUseCase>() with provider { GetUpcomingMoviesUseCase(instance()) }
    bind<LoadPopularMoviesUseCase>() with provider { LoadPopularMoviesUseCase(instance()) }
    bind<GetPopularMoviesPageCountUseCase>() with provider { GetPopularMoviesPageCountUseCase(instance()) }
    bind<GetPopularMoviesUseCase>() with provider { GetPopularMoviesUseCase(instance(), instance(), instance()) }
}