package com.rasalexman.data.source.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.core.data.base.IRemoteDataSource
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.models.remote.MovieModel

interface IMoviesRemoteDataSource : IRemoteDataSource {

    suspend fun getSearchDataSource(query: String, resultLiveData: MutableLiveData<SResult<Boolean>>): DataSource.Factory<Int, MovieModel>

    suspend fun getByGenreId(genreId: Int, lastReleaseDate: Long?): ResultList<MovieModel>
    suspend fun getPopularMovies(page: Int): ResultList<MovieModel>
    suspend fun getTopRatedMovies(page: Int): ResultList<MovieModel>

    suspend fun getMovieDetails(movieId: Int): SResult<MovieModel>

    suspend fun getNewMoviesByGenreId(genreId: Int): ResultList<MovieModel>


    suspend fun getUpcomingMovies(page: Int): ResultList<MovieModel>
}