package com.rasalexman.providers.data.source.local

import androidx.paging.DataSource
import com.rasalexman.core.data.base.ILocalDataSource
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.providers.data.models.local.MovieEntity

interface IMoviesLocalDataSource : ILocalDataSource {

    suspend fun getDataSourceFactory(genreId: Int): DataSource.Factory<Int, MovieEntity>

    suspend fun getCount(genreId: Int): Int

    suspend fun getById(movieId: Int): SResult<MovieEntity>

    suspend fun insertAll(data: List<MovieEntity>)

    suspend fun insert(data: MovieEntity)
}