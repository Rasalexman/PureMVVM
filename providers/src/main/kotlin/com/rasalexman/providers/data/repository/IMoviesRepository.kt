package com.rasalexman.providers.data.repository

import androidx.paging.DataSource
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.core.common.typealiases.ResultMutableLiveData
import com.rasalexman.core.data.base.IBaseRepository
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.models.local.MovieEntity
import com.rasalexman.providers.data.source.local.IMoviesLocalDataSource
import com.rasalexman.providers.data.source.remote.IMoviesRemoteDataSource

interface IMoviesRepository : IBaseRepository<IMoviesLocalDataSource, IMoviesRemoteDataSource> {

    suspend fun getPopularMoviesDataSource(): DataSource.Factory<Int, MovieEntity>
    suspend fun getPopularMoviesCount(): Int

    suspend fun getMoviesByGenreDataSource(genreId: Int): DataSource.Factory<Int, MovieEntity>

    suspend fun saveMoviesList(data: List<MovieEntity>)

    suspend fun saveMovie(data: MovieEntity)

    suspend fun getMovieById(movieId: Int): SResult<MovieEntity>

    suspend fun getRemoteSearchDataSource(
        query: String,
        resultLiveData: ResultMutableLiveData<Boolean>
    ): DataSource.Factory<Int, MovieEntity>


    suspend fun getRemoteMovies(genreId: Int, lastReleaseDate: Long?): ResultList<MovieEntity>
    suspend fun getRemotePopularMovies(page: Int): ResultList<MovieEntity>
    suspend fun getRemoteTopRatedMovies(page: Int): ResultList<MovieEntity>
    suspend fun getRemoteUpcomingMovies(page: Int): ResultList<MovieEntity>
}