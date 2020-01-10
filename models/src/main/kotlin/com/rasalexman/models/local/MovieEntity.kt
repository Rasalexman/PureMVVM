package com.rasalexman.models.local

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.rasalexman.core.data.base.IConvertableTo
import com.rasalexman.models.ui.MovieItemUI
import java.text.SimpleDateFormat
import java.util.*

@Keep
@Entity(
    indices = [
        Index(
            value = ["title"],
            unique = true
        ),
        Index(
            value = ["id"],
            unique = true
        )
    ]
)
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val voteCount: Int,
    val voteAverage: Double,
    val isVideo: Boolean,
    val title: String,
    val popularity: Double,
    val posterPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val genreIds: List<Int>,
    val backdropPath: String,
    val releaseDate: Long,
    val adult: Boolean,
    val overview: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val budget: Long,
    var hasDetails: Boolean = false,
    var isPopular: Boolean = false,
    var isTopRated: Boolean = false,
    var isUpComing: Boolean = false
) : IConvertableTo<MovieItemUI> {
    override fun convertTo(): MovieItemUI {
        return MovieItemUI(
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
    }
}