package com.rasalexman.tabevents

import com.mincor.kodi.core.*
import com.rasalexman.tabevents.domain.GetPopularMoviesUseCase
import com.rasalexman.tabevents.domain.LoadPopularMoviesUseCase

val tabEventsModule = kodiModule {
    bind<LoadPopularMoviesUseCase>() with provider { LoadPopularMoviesUseCase(instance()) }
    bind<GetPopularMoviesUseCase>() with provider { GetPopularMoviesUseCase(instance(), instance()) }
}