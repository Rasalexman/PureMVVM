package com.rasalexman.data.source.remote

import com.rasalexman.core.data.base.IRemoteDataSource
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.models.local.GenreEntity
import com.rasalexman.models.remote.GenreModel

interface IGenresRemoteDataSource : IRemoteDataSource {
    suspend fun getRemoteGenresList(): SResult<List<GenreModel>>
    suspend fun getGenresImages(result: List<GenreEntity>)
}