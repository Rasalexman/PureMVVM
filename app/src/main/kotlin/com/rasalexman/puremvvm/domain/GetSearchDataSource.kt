package com.rasalexman.puremvvm.domain

import androidx.paging.LivePagedListBuilder
import com.rasalexman.core.common.typealiases.PagedLiveData
import com.rasalexman.core.common.typealiases.ResultMutableLiveData
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.providers.data.repository.IMoviesRepository

class GetSearchDataSource(
    private val movieRepository: IMoviesRepository
) : IUseCase.DoubleInOut<String, ResultMutableLiveData<Boolean>, PagedLiveData<MovieEntity>> {
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(
        query: String,
        resultLiveData: ResultMutableLiveData<Boolean>
    ): PagedLiveData<MovieEntity> {
        val searchDataSourceFactory = movieRepository.getRemoteSearchDataSource(query, resultLiveData)
        val pageLiveData = LivePagedListBuilder(searchDataSourceFactory, 20)
        return pageLiveData.build()
    }
}