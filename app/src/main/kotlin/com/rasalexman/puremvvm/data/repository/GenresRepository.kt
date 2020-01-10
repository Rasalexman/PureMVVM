package com.rasalexman.puremvvm.data.repository

import com.rasalexman.core.common.extensions.applyIfSuccessSuspend
import com.rasalexman.core.common.extensions.mapListTo
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.models.local.GenreEntity
import com.rasalexman.providers.data.repository.IGenresRepository
import com.rasalexman.providers.data.source.local.IGenresLocalDataSource
import com.rasalexman.providers.data.source.remote.IGenresRemoteDataSource

class GenresRepository(
    override val localDataSource: IGenresLocalDataSource,
    override val remoteDataSource: IGenresRemoteDataSource
) : IGenresRepository {

    override suspend fun getLocalGenresList(): SResult<List<GenreEntity>> {
        return localDataSource.getGenresList()
    }

    override suspend fun getRemoteGenresList(): SResult<List<GenreEntity>> {
        return remoteDataSource.getRemoteGenresList()
            .mapListTo()
            .applyIfSuccessSuspend(remoteDataSource::getGenresImages)
    }

    override suspend fun saveGenresList(genresList: List<GenreEntity>) {
        localDataSource.saveGenresList(genresList)
    }
}