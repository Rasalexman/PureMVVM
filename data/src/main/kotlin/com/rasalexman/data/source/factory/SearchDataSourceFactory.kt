package com.rasalexman.data.source.factory

import androidx.paging.DataSource
import com.rasalexman.core.common.typealiases.ResultMutableLiveData
import com.rasalexman.models.remote.MovieModel
import com.rasalexman.providers.network.api.IMovieApi
import com.rasalexman.data.source.remote.SearchRemoteDataSource

class SearchDataSourceFactory(
    private val movieApi: IMovieApi,
    private val query: String,
    private val resultLiveData: ResultMutableLiveData<Boolean>
) : DataSource.Factory<Int, MovieModel>() {
    override fun create(): DataSource<Int, MovieModel> {
        return SearchRemoteDataSource(
            movieApi,
            query,
            resultLiveData
        )
    }
}