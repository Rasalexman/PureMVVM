package com.rasalexman.puremvvm.data.source.local

import androidx.paging.DataSource
import com.rasalexman.core.common.extensions.emptyResult
import com.rasalexman.core.common.extensions.successResult
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.providers.data.source.local.IMoviesLocalDataSource
import com.rasalexman.providers.database.dao.IMoviesDao

class MoviesLocalDataSource(
    private val moviesDao: IMoviesDao
) : IMoviesLocalDataSource {

    override suspend fun getMoviesByGenreDataSourceFactory(genreId: Int): DataSource.Factory<Int, MovieEntity> =
        moviesDao.getAllByGenreId(genreId)

    override suspend fun getPopularMoviesDataSourceFactory(): DataSource.Factory<Int, MovieEntity> {
        return moviesDao.getAllByPopular()
    }

    override suspend fun getById(movieId: Int): SResult<MovieEntity> =
        moviesDao.getById(movieId)?.let { localMovie ->
            if(localMovie.hasDetails) successResult(localMovie)
            else emptyResult()
        } ?: emptyResult()

    override suspend fun getPopularMoviesCount(): Int {
        return moviesDao.getPopularCount()
    }

    override suspend fun getCount(genreId: Int): Int {
        return moviesDao.getCount(genreId)
    }

    override suspend fun insertAll(data: List<MovieEntity>) {
        moviesDao.insertAll(data)
    }

    override suspend fun insert(data: MovieEntity) {
        moviesDao.insert(data)
    }
}