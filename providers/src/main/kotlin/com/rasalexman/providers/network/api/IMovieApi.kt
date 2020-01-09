package com.rasalexman.providers.network.api

import com.rasalexman.providers.data.models.remote.MovieModel
import com.rasalexman.providers.network.responses.GetGenresResponse
import com.rasalexman.providers.network.responses.GetMoviesListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

interface IMovieApi {

    /**
     * Get the list of official genres for movies.
     */
    @GET("genre/movie/list")
    suspend fun getGenresList(): Response<GetGenresResponse>

    @GET("discover/movie")
    suspend fun getMoviesListByPopularity(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1,
        @Query("sort_by") sort_by: String = "popularity.desc"
    ): Response<GetMoviesListResponse<MovieModel>>

    @GET("discover/movie")
    suspend fun getMoviesListByGenreId(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1,
        @Query("release_date.lte") lte: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
        @Query("sort_by") sortBy: String = "release_date.desc"
    ): Response<GetMoviesListResponse<MovieModel>>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): Response<MovieModel>

    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): Response<GetMoviesListResponse<MovieModel>>

    @GET("movie/popular")
    suspend fun getPopularMovie(@Query("page") page: Int = 1): Response<GetMoviesListResponse<MovieModel>>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int = 1): Response<GetMoviesListResponse<MovieModel>>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int = 1): Response<GetMoviesListResponse<MovieModel>>
}