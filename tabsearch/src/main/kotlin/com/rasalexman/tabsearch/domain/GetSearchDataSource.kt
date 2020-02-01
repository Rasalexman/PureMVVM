package com.rasalexman.tabsearch.domain

import androidx.paging.toLiveData
import com.rasalexman.core.common.typealiases.AnyResultMutableLiveData
import com.rasalexman.core.common.typealiases.PagedLiveData
import com.rasalexman.data.repository.IMoviesRepository
import com.rasalexman.models.ui.MovieItemUI
import com.rasalexman.tabsearch.BuildConfig

internal class GetSearchDataSource(
    private val movieRepository: IMoviesRepository
) : IGetSearchDataSource {
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(
        query: String,
        resultLiveData: AnyResultMutableLiveData
    ): PagedLiveData<MovieItemUI> {
        return  movieRepository
            .getRemoteSearchDataSource(query, resultLiveData)
            .map { it.convertTo() }
            .toLiveData(BuildConfig.ITEMS_ON_PAGE)
    }
}