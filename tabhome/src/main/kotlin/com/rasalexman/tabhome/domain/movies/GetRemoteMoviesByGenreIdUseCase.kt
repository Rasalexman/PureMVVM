package com.rasalexman.tabhome.domain.movies

import com.rasalexman.core.common.extensions.applyIfSuccessSuspend
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.models.local.MovieEntity
import com.rasalexman.providers.data.repository.IMoviesRepository

class GetRemoteMoviesByGenreIdUseCase(
    private val moviesRepository: IMoviesRepository
) : IUseCase.DoubleInOut<Int, Long?, ResultList<MovieEntity>> {
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(genreId: Int, lastReleaseDate: Long?): ResultList<MovieEntity> {
        return moviesRepository.getRemoteMovies(genreId, lastReleaseDate)
            .applyIfSuccessSuspend(moviesRepository::saveMoviesList)
    }
}