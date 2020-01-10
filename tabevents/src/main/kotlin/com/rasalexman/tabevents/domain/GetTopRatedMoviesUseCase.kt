package com.rasalexman.tabevents.domain

import com.rasalexman.core.common.extensions.mapListBy
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.providers.data.repository.IMoviesRepository
import com.rasalexman.tabhome.data.convert
import com.rasalexman.tabhome.presentation.movieslist.MovieItemUI

class GetTopRatedMoviesUseCase(
    private val moviesRepository: IMoviesRepository
) : IUseCase.SingleInOut<Int, ResultList<MovieItemUI>> {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(page: Int): ResultList<MovieItemUI> {
        return moviesRepository.getRemoteTopRatedMovies(page).mapListBy { convert() }
    }
}