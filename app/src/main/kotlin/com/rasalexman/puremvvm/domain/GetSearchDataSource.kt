package com.rasalexman.puremvvm.domain

import androidx.paging.LivePagedListBuilder
import com.rasalexman.core.common.typealiases.PagedLiveData
import com.rasalexman.core.common.typealiases.ResultMutableLiveData
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.providers.data.repository.IMoviesRepository
import com.rasalexman.tabhome.data.convert
import com.rasalexman.tabhome.presentation.movieslist.MovieItemUI

class GetSearchDataSource(
    private val movieRepository: IMoviesRepository
) : IUseCase.DoubleInOut<String, ResultMutableLiveData<Boolean>, PagedLiveData<MovieItemUI>> {
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(
        query: String,
        resultLiveData: ResultMutableLiveData<Boolean>
    ): PagedLiveData<MovieItemUI> {
        val searchDataSourceFactory = movieRepository.getRemoteSearchDataSource(query, resultLiveData).map { it.convert() }
        val pageLiveData = LivePagedListBuilder(searchDataSourceFactory, 20)
        return pageLiveData.build()
    }
}