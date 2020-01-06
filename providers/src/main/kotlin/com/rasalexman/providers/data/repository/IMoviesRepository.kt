package com.rasalexman.providers.data.repository

import androidx.paging.DataSource
import com.rasalexman.core.data.base.IBaseRepository
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.providers.data.source.local.IMoviesLocalDataSource
import com.rasalexman.providers.data.source.remote.IMoviesRemoteDataSource

interface IMoviesRepository : IBaseRepository<IMoviesLocalDataSource, IMoviesRemoteDataSource> {

    suspend fun getLocalMoviesDataSource(genreId: Int): DataSource.Factory<Int, MovieEntity>

    suspend fun getRemoteMovies(genreId: Int, lastReleaseDate: Long?): SResult<List<MovieEntity>>

    suspend fun saveMoviesList(data: List<MovieEntity>)

    suspend fun saveMovie(data: MovieEntity)

    suspend fun getMovieById(movieId: Int): SResult<MovieEntity>
}