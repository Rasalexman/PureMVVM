package com.rasalexman.providers.data.repository

import com.rasalexman.core.data.base.IBaseRepository
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.providers.data.models.local.GenreEntity
import com.rasalexman.providers.data.source.local.IGenresLocalDataSource
import com.rasalexman.providers.data.source.remote.IGenresRemoteDataSource

interface IGenresRepository : IBaseRepository<IGenresLocalDataSource, IGenresRemoteDataSource> {

    suspend fun getLocalGenresList(): SResult<List<GenreEntity>>
    suspend fun getRemoteGenresList(): SResult<List<GenreEntity>>

    suspend fun saveGenresList(genresList: List<GenreEntity>)
}