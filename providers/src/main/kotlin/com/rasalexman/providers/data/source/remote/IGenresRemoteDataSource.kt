package com.rasalexman.providers.data.source.remote

import com.rasalexman.core.data.base.IRemoteDataSource
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.providers.data.models.local.GenreEntity
import com.rasalexman.providers.data.models.remote.GenreModel

interface IGenresRemoteDataSource : IRemoteDataSource {
    suspend fun getRemoteGenresList(): SResult<List<GenreModel>>
    suspend fun getGenresImages(result: List<GenreEntity>)
}