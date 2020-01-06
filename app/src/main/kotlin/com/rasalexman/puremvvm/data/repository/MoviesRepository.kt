package com.rasalexman.puremvvm.data.repository

import androidx.paging.DataSource
import com.rasalexman.core.common.extensions.mapListTo
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.providers.data.repository.IMoviesRepository
import com.rasalexman.providers.data.source.local.IMoviesLocalDataSource
import com.rasalexman.providers.data.source.remote.IMoviesRemoteDataSource

class MoviesRepository(
    override val localDataSource: IMoviesLocalDataSource,
    override val remoteDataSource: IMoviesRemoteDataSource
) : IMoviesRepository {

    override suspend fun getLocalMoviesDataSource(genreId: Int): DataSource.Factory<Int, MovieEntity> {
        return localDataSource.getDataSourceFactory(genreId)
    }

    override suspend fun getRemoteMovies(
        genreId: Int,
        lastReleaseDate: Long?
    ): SResult<List<MovieEntity>> {
        return remoteDataSource
            .getByGenreId(genreId, lastReleaseDate)
            .mapListTo()
    }

    override suspend fun saveMoviesList(data: List<MovieEntity>) {
        localDataSource.insertAll(data)
    }

    override suspend fun saveMovie(data: MovieEntity) = localDataSource.insert(data)

    override suspend fun getMovieById(movieId: Int): SResult<MovieEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}