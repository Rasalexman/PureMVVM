package com.rasalexman.providers.data.source.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rasalexman.core.data.base.IRemoteDataSource
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.providers.data.models.remote.MovieModel

interface IMoviesRemoteDataSource : IRemoteDataSource {

    suspend fun getSearchDataSource(query: String, resultLiveData: MutableLiveData<SResult<Boolean>>): DataSource.Factory<Int, MovieModel>

    suspend fun getByGenreId(genreId: Int, lastReleaseDate: Long?): SResult<List<MovieModel>>

    suspend fun getMovieDetails(movieId: Int): SResult<MovieModel>

    suspend fun getNewMoviesByGenreId(genreId: Int): SResult<List<MovieModel>>
}