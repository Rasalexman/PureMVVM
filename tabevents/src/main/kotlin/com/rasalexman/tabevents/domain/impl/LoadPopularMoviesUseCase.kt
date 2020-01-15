package com.rasalexman.tabevents.domain.impl

import com.rasalexman.core.common.extensions.applyIfSuccessSuspend
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.data.repository.IMoviesRepository
import com.rasalexman.models.local.MovieEntity
import com.rasalexman.tabevents.domain.ILoadPopularMoviesUseCase

internal class LoadPopularMoviesUseCase(
    private val moviesRepository: IMoviesRepository
) : ILoadPopularMoviesUseCase {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(page: Int): ResultList<MovieEntity> {
        return moviesRepository.getRemotePopularMovies(page)
            .applyIfSuccessSuspend(moviesRepository::saveMoviesList)
    }
}