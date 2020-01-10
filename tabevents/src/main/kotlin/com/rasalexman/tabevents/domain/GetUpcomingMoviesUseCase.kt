package com.rasalexman.tabevents.domain

import com.rasalexman.core.common.extensions.mapListTo
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.data.repository.IMoviesRepository
import com.rasalexman.models.ui.MovieItemUI

class GetUpcomingMoviesUseCase(
    private val moviesRepository: IMoviesRepository
) : IUseCase.SingleInOut<Int, ResultList<MovieItemUI>> {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(page: Int): ResultList<MovieItemUI> {
        return moviesRepository.getRemoteUpcomingMovies(page).mapListTo()
    }
}