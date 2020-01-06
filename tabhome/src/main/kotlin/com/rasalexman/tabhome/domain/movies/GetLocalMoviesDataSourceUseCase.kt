package com.rasalexman.tabhome.domain.movies

import androidx.paging.DataSource
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.providers.data.repository.IMoviesRepository

class GetLocalMoviesDataSourceUseCase(
    private val moviesRepository: IMoviesRepository
) : IUseCase.SingleInOut<Int, DataSource.Factory<Int, MovieEntity>> {
    override suspend fun execute(data: Int): DataSource.Factory<Int, MovieEntity> {
        return moviesRepository.getLocalMoviesDataSource(data)
    }
}