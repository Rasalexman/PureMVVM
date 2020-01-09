package com.rasalexman.tabevents.domain

import com.rasalexman.core.common.extensions.applyIfSuccessSuspend
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.providers.data.repository.IMoviesRepository

class LoadPopularMoviesUseCase(
    private val moviesRepository: IMoviesRepository
) : IUseCase.Out<ResultList<MovieEntity>> {

    private var page: Int = 1

    override suspend fun execute(): ResultList<MovieEntity> {
        return moviesRepository.getRemotePopularMovies(page++)
            .applyIfSuccessSuspend(moviesRepository::saveMoviesList)
    }
}