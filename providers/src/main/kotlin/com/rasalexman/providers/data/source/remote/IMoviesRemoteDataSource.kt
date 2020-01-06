package com.rasalexman.providers.data.source.remote

import com.rasalexman.core.data.base.IRemoteDataSource
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.providers.data.models.remote.MovieModel

interface IMoviesRemoteDataSource : IRemoteDataSource {

    suspend fun getByGenreId(genreId: Int): SResult<List<MovieModel>>

    suspend fun getMovieDetails(movieId: Int): SResult<MovieModel>

    suspend fun getNewMoviesByGenreId(genreId: Int): SResult<List<MovieModel>>

    fun clearPage()
    fun upPage()
}