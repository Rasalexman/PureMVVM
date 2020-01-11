package com.rasalexman.tabevents.domain

import com.rasalexman.core.domain.IUseCase
import com.rasalexman.data.repository.IMoviesRepository
import com.rasalexman.tabevents.BuildConfig

class GetPopularMoviesPageCountUseCase(
    private val moviesRepository: IMoviesRepository
    ) : IUseCase.Out<Int> {
    override suspend fun execute(): Int {
        val localCount = moviesRepository.getPopularMoviesCount()
        return (localCount / BuildConfig.ITEMS_ON_PAGE).takeIf { it > 0 } ?: 1
    }
}