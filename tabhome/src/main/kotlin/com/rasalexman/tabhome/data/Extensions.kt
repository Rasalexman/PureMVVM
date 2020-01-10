package com.rasalexman.tabhome.data

import com.rasalexman.providers.data.models.local.GenreEntity
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.tabhome.presentation.genreslist.GenreItemUI
import com.rasalexman.tabhome.presentation.movieslist.MovieItemUI
import java.text.SimpleDateFormat
import java.util.*

fun GenreEntity.convert() = GenreItemUI(id = this.id, name = this.name, images = this.images)
fun MovieEntity.convert() = MovieItemUI(
    id = id,
    voteCount = voteCount,
    voteAverage = voteAverage,
    isVideo = isVideo,
    title = title,
    popularity = popularity,
    posterPath = posterPath.takeIf { it.isNotEmpty() } ?: backdropPath,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    genreIds = genreIds,
    backdropPath = backdropPath,
    originalReleaseDate = releaseDate,
    releaseDate = releaseDate.takeIf { dt ->
        dt > 0L
    }?.let {
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(
            Date(
                releaseDate
            )
        )
    }.orEmpty(),
    adult = adult,
    overview = overview
)