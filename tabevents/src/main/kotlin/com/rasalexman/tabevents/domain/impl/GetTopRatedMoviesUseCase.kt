package com.rasalexman.tabevents.domain.impl

import androidx.lifecycle.LiveData
import com.rasalexman.core.common.extensions.mapListTo
import com.rasalexman.core.common.typealiases.FlowResultList
import com.rasalexman.data.repository.IMoviesRepository
import com.rasalexman.models.ui.MovieItemUI
import com.rasalexman.tabevents.domain.IGetTopRatedMoviesFlowUseCase
import kotlinx.coroutines.flow.map

internal class GetTopRatedMoviesUseCase(
    private val moviesRepository: IMoviesRepository
) : IGetTopRatedMoviesFlowUseCase {

    override suspend fun execute(data: LiveData<Int>): FlowResultList<MovieItemUI> {
        return moviesRepository.getRemoteTopRatedMovies(data).map {
            it.mapListTo()
        }
    }
}