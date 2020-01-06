package com.rasalexman.puremvvm.modules

import com.mincor.kodi.core.*
import com.rasalexman.providers.data.source.remote.IGenresRemoteDataSource
import com.rasalexman.providers.data.source.remote.IMoviesRemoteDataSource
import com.rasalexman.puremvvm.data.source.remote.GenresRemoteDataSource
import com.rasalexman.puremvvm.data.source.remote.MoviesRemoteDataSource

val remoteDataSourceModule = kodiModule {
    bind<IGenresRemoteDataSource>() with single {
        GenresRemoteDataSource(
            instance()
        )
    }

    bind<IMoviesRemoteDataSource>() with single {
        MoviesRemoteDataSource(
            instance()
        )
    }
}