package com.rasalexman.data

import com.mincor.kodi.core.*
import com.rasalexman.data.source.remote.GenresRemoteDataSource
import com.rasalexman.data.source.remote.IGenresRemoteDataSource
import com.rasalexman.data.source.remote.IMoviesRemoteDataSource
import com.rasalexman.data.source.remote.MoviesRemoteDataSource

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