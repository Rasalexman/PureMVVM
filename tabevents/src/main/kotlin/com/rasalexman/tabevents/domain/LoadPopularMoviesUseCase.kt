package com.rasalexman.tabevents.domain

import com.rasalexman.core.common.extensions.applyIfSuccessSuspend
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.data.repository.IMoviesRepository
import com.rasalexman.models.local.MovieEntity

class LoadPopularMoviesUseCase(
    private val moviesRepository: IMoviesRepository
) : IUseCase.SingleInOut<Int, ResultList<MovieEntity>> {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(page: Int): ResultList<MovieEntity> {
        return moviesRepository.getRemotePopularMovies(page)
            .applyIfSuccessSuspend(moviesRepository::saveMoviesList)
    }
}