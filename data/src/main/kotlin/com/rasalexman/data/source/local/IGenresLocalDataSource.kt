package com.rasalexman.data.source.local

import com.rasalexman.core.data.base.ILocalDataSource
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.models.local.GenreEntity

interface IGenresLocalDataSource : ILocalDataSource {
    suspend fun getGenresList(): SResult<List<GenreEntity>>
    suspend fun saveGenresList(data: List<GenreEntity>)
}