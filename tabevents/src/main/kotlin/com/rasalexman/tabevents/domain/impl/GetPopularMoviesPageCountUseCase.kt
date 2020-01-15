package com.rasalexman.tabevents.domain.impl

import com.rasalexman.data.repository.IMoviesRepository
import com.rasalexman.tabevents.BuildConfig
import com.rasalexman.tabevents.domain.IGetPopularMoviesPageCountUseCase

internal class GetPopularMoviesPageCountUseCase(
    private val moviesRepository: IMoviesRepository
    ) : IGetPopularMoviesPageCountUseCase {
    override suspend fun execute(): Int {
        val localCount = moviesRepository.getPopularMoviesCount()
        return (localCount / BuildConfig.ITEMS_ON_PAGE).takeIf { it > 0 } ?: 1
    }
}