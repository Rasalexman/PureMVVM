package com.rasalexman.tabevents.domain.impl

import com.rasalexman.core.common.extensions.mapListTo
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.data.repository.IMoviesRepository
import com.rasalexman.models.ui.MovieItemUI
import com.rasalexman.tabevents.domain.IGetUpcomingMoviesUseCase

internal class GetUpcomingMoviesUseCase(
    private val moviesRepository: IMoviesRepository
) : IGetUpcomingMoviesUseCase {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(page: Int): ResultList<MovieItemUI> {
        return moviesRepository.getRemoteUpcomingMovies(page).mapListTo()
    }
}