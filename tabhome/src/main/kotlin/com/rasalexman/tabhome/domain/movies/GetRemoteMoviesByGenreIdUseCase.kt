package com.rasalexman.tabhome.domain.movies

import com.rasalexman.core.common.extensions.applyIfSuccessSuspend
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.providers.data.repository.IMoviesRepository

class GetRemoteMoviesByGenreIdUseCase(
    private val moviesRepository: IMoviesRepository
) : IUseCase.SingleIn<Int> {
    override suspend fun execute(data: Int) {
        moviesRepository.getRemoteMovies(data)
            .applyIfSuccessSuspend(moviesRepository::saveMoviesList)
    }
}