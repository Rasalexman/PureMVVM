package com.rasalexman.puremvvm.data.source.remote

import com.rasalexman.core.common.extensions.emptyResult
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.providers.data.models.remote.MovieModel
import com.rasalexman.providers.data.source.remote.IMoviesRemoteDataSource
import com.rasalexman.providers.network.api.IMovieApi
import com.rasalexman.providers.network.responses.GetMoviesListResponse
import com.rasalexman.providers.network.responses.getResult
import java.text.SimpleDateFormat
import java.util.*

class MoviesRemoteDataSource(
    private val moviesApi: IMovieApi
) : IMoviesRemoteDataSource {

    private var currentPage = 1
    private var maxPages = 1000

    private val isCanChangePage: Boolean
        get() = currentPage < maxPages

    /**
     * Get movies by genre id with pagination
     */
    override suspend fun getByGenreId(
        genreId: Int,
        lastReleaseDate: Long?
    ): SResult<List<MovieModel>> =
        if (isCanChangePage) {
            requestHandler(genreId = genreId, page = currentPage, lastReleaseDate = lastReleaseDate)
        } else emptyResult()

    /**
     * Get New movies list by genre id starting from first page (for swipe to refresh)
     */
    override suspend fun getNewMoviesByGenreId(genreId: Int): SResult<List<MovieModel>> {
        return requestHandler(genreId = genreId, page = 1)
    }

    /**
     * Get all the movie details for current id
     */
    override suspend fun getMovieDetails(movieId: Int): SResult<MovieModel> {
        return moviesApi.getMovieDetails(movieId).getResult { it }
    }

    override fun upPage() {
        currentPage = 2
    }

    /**
     * Clear Paging
     */
    override fun clearPage() {
        currentPage = 1
        maxPages = 1000
    }

    /**
     * Request data from API
     */
    private suspend fun requestHandler(
        genreId: Int,
        page: Int,
        lastReleaseDate: Long? = null
    ): SResult<List<MovieModel>> {
        val releaseDate = lastReleaseDate?.run { Date(lastReleaseDate) } ?: Date()
        val releaseDateFrom = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(releaseDate)
        return moviesApi.getMoviesListByGenreId(genreId, 1, releaseDateFrom).getResult { response ->
            response.results
        }
    }


    /**
     * Increase currentPage count if available
     *
     * @param response - current request response data
     */
    private fun changePage(response: GetMoviesListResponse<MovieModel>) {
        val currentPage = response.page
        maxPages = response.total_pages
        if (currentPage < maxPages) this.currentPage++
    }
}