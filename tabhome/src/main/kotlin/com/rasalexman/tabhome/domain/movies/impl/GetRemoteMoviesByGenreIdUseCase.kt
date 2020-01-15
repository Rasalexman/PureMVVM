package com.rasalexman.tabhome.domain.movies.impl

import com.rasalexman.core.common.extensions.applyIfSuccessSuspend
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.data.repository.IMoviesRepository
import com.rasalexman.models.local.MovieEntity
import com.rasalexman.tabhome.domain.movies.IGetRemoteMoviesByGenreIdUseCase

internal class GetRemoteMoviesByGenreIdUseCase(
    private val moviesRepository: IMoviesRepository
) : IGetRemoteMoviesByGenreIdUseCase {
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(genreId: Int, lastReleaseDate: Long?): ResultList<MovieEntity> {
        return moviesRepository.getRemoteMovies(genreId, lastReleaseDate)
            .applyIfSuccessSuspend(moviesRepository::saveMoviesList)
    }
}