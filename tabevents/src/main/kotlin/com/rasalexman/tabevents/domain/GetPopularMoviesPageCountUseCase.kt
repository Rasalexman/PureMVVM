package com.rasalexman.tabevents.domain

import com.rasalexman.core.domain.IUseCase
import com.rasalexman.providers.data.repository.IMoviesRepository
import com.rasalexman.tabevents.BuildConfig

class GetPopularMoviesPageCountUseCase(
    private val moviesRepository: IMoviesRepository
    ) : IUseCase.Out<Int> {
    override suspend fun execute(): Int {
        return moviesRepository.getPopularMoviesCount() / BuildConfig.ITEMS_ON_PAGE
    }
}