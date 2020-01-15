package com.rasalexman.tabevents.domain.impl

import com.rasalexman.core.common.extensions.mapListTo
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.data.repository.IMoviesRepository
import com.rasalexman.models.ui.MovieItemUI
import com.rasalexman.tabevents.domain.IGetTopRatedMoviesUseCase

internal class GetTopRatedMoviesUseCase(
    private val moviesRepository: IMoviesRepository
) : IGetTopRatedMoviesUseCase {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(page: Int): ResultList<MovieItemUI> {
        return moviesRepository.getRemoteTopRatedMovies(page).mapListTo()
    }
}