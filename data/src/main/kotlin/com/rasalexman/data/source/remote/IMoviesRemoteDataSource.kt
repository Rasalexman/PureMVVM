package com.rasalexman.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rasalexman.core.common.typealiases.FlowResultList
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.core.data.base.IRemoteDataSource
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.models.remote.MovieModel
import kotlinx.coroutines.flow.Flow

interface IMoviesRemoteDataSource : IRemoteDataSource {

    suspend fun getSearchDataSource(query: String, resultLiveData: MutableLiveData<SResult<Any>>): DataSource.Factory<Int, MovieModel>

    suspend fun getByGenreId(genreId: Int, lastReleaseDate: Long?): ResultList<MovieModel>


    suspend fun getMovieDetails(movieId: Int): SResult<MovieModel>

    suspend fun getNewMoviesByGenreId(genreId: Int): ResultList<MovieModel>

    suspend fun getPopularMovies(page: Int): ResultList<MovieModel>
    suspend fun getTopRatedMovies(pageLiveData: LiveData<Int>): FlowResultList<MovieModel>
    suspend fun getUpcomingMovies(page: Int): ResultList<MovieModel>


}