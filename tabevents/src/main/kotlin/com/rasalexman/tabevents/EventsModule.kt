package com.rasalexman.tabevents

import com.mincor.kodi.core.*
import com.rasalexman.tabevents.domain.GetPopularMoviesPageCountUseCase
import com.rasalexman.tabevents.domain.GetPopularMoviesUseCase
import com.rasalexman.tabevents.domain.GetTopRatedMoviesUseCase
import com.rasalexman.tabevents.domain.LoadPopularMoviesUseCase

val tabEventsModule = kodiModule {
    bind<GetTopRatedMoviesUseCase>() with provider { GetTopRatedMoviesUseCase(instance()) }
    bind<LoadPopularMoviesUseCase>() with provider { LoadPopularMoviesUseCase(instance()) }
    bind<GetPopularMoviesPageCountUseCase>() with provider { GetPopularMoviesPageCountUseCase(instance()) }
    bind<GetPopularMoviesUseCase>() with provider { GetPopularMoviesUseCase(instance(), instance(), instance()) }
}