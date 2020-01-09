package com.rasalexman.providers.data.models.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

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
)