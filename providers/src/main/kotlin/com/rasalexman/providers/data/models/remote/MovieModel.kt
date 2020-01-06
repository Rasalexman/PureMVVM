package com.rasalexman.providers.data.models.remote

import com.rasalexman.core.data.base.IConvertableTo
import com.rasalexman.providers.data.models.local.MovieEntity
import java.text.SimpleDateFormat
import java.util.*

data class MovieModel(
    val id: Int,
    val vote_count: Int?,
    val vote_average: Double?,
    val video: Boolean?,
    val title: String?,
    val popularity: Double?,
    val poster_path: String?,
    val original_language: String?,
    val original_title: String?,
    val genre_ids: List<Int>?,
    val backdrop_path: String?,
    val release_date: String?,
    val adult: Boolean?,
    val overview: String?,
    val revenue: Int?,
    val runtime: Int?,
    val status: String?,
    val tagline: String?,
    val budget: Long?
) : IConvertableTo<MovieEntity> {
    override fun convertTo(): MovieEntity? {
        return release_date?.run {
            MovieEntity(
                id = id,
                voteCount = vote_count ?: 0,
                voteAverage = vote_average ?: 0.0,
                isVideo = video ?: false,
                title = title.orEmpty(),
                popularity = popularity ?: 0.0,
                posterPath = poster_path.orEmpty(),
                originalLanguage = original_language.orEmpty(),
                originalTitle = original_title.orEmpty(),
                genreIds = genre_ids ?: emptyList(),
                backdropPath = backdrop_path.orEmpty(),
                releaseDate = parseReleaseDate(),
                adult = adult ?: false,
                overview = overview.orEmpty(),
                revenue = revenue ?: 0,
                runtime = runtime ?: 0,
                status = status.orEmpty(),
                tagline = tagline.orEmpty(),
                budget = budget ?: 0L
            )
        }
    }

    private fun parseReleaseDate(): Long {
        return release_date?.let {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it)?.time ?: 0L
        } ?: 0L
    }

    fun getImagePath() = poster_path ?: backdrop_path.orEmpty()
}