package com.rasalexman.tabhome.domain.movies

import com.rasalexman.core.common.extensions.applyIfSuccessSuspend
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.providers.data.repository.IMoviesRepository

class GetRemoteMoviesByGenreIdUseCase(
    private val moviesRepository: IMoviesRepository
) : IUseCase.DoubleInOut<Int, Long?, Unit> {
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(genreId: Int, lastReleaseDate: Long?) {
        moviesRepository.getRemoteMovies(genreId, lastReleaseDate)
            .applyIfSuccessSuspend(moviesRepository::saveMoviesList)
    }
}