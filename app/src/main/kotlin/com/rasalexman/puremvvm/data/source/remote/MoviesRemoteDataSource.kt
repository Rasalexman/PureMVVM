package com.rasalexman.puremvvm.data.source.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.providers.data.models.remote.MovieModel
import com.rasalexman.providers.data.source.remote.IMoviesRemoteDataSource
import com.rasalexman.providers.network.api.IMovieApi
import com.rasalexman.providers.network.responses.getResult
import com.rasalexman.puremvvm.data.source.factory.SearchDataSourceFactory
import java.text.SimpleDateFormat
import java.util.*

class MoviesRemoteDataSource(
    private val moviesApi: IMovieApi
) : IMoviesRemoteDataSource {

    override suspend fun getSearchDataSource(
        query: String,
        resultLiveData: MutableLiveData<SResult<Boolean>>
    ): DataSource.Factory<Int, MovieModel> {
        return SearchDataSourceFactory(
            moviesApi,
            query,
            resultLiveData
        )
    }

    /**
     * Get movies by genre id with pagination
     */
    override suspend fun getByGenreId(
        genreId: Int,
        lastReleaseDate: Long?
    ): SResult<List<MovieModel>> =
        requestHandler(genreId = genreId, lastReleaseDate = lastReleaseDate)

    /**
     * Get New movies list by genre id starting from first page (for swipe to refresh)
     */
    override suspend fun getNewMoviesByGenreId(genreId: Int): SResult<List<MovieModel>> {
        return requestHandler(genreId = genreId)
    }

    /**
     * Get all the movie details for current id
     */
    override suspend fun getMovieDetails(movieId: Int): SResult<MovieModel> {
        return moviesApi.getMovieDetails(movieId).getResult { it }
    }

    /**
     * Request data from API
     */
    private suspend fun requestHandler(
        genreId: Int,
        lastReleaseDate: Long? = null
    ): SResult<List<MovieModel>> {
        val releaseDate = lastReleaseDate?.run { Date(lastReleaseDate) } ?: Date()
        val releaseDateFrom =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(releaseDate)
        return moviesApi.getMoviesListByGenreId(genreId, 1, releaseDateFrom).getResult { response ->
            response.results
        }
    }
}