package com.rasalexman.tabevents.domain

import com.rasalexman.core.common.extensions.mapIfSuccessSuspend
import com.rasalexman.core.common.extensions.toSuccessResult
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.providers.data.repository.IMoviesRepository
import com.rasalexman.tabhome.presentation.movieslist.MovieItemUI
import java.text.SimpleDateFormat
import java.util.*

class GetTopRatedMoviesUseCase(
    private val moviesRepository: IMoviesRepository
) : IUseCase.SingleInOut<Int, ResultList<MovieItemUI>> {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(page: Int): ResultList<MovieItemUI> {
        return moviesRepository.getRemoteTopRatedMovies(page).mapIfSuccessSuspend {
            this.map {
                it.run {
                    MovieItemUI(
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
            }.toSuccessResult()
        }
    }
}