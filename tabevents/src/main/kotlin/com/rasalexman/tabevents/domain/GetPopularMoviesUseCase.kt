package com.rasalexman.tabevents.domain

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rasalexman.core.common.extensions.loadingResult
import com.rasalexman.core.common.typealiases.PagedLiveData
import com.rasalexman.core.common.typealiases.ResultMutableLiveData
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.doWithAsync
import com.rasalexman.coroutinesmanager.launchOnUI
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.providers.data.repository.IMoviesRepository

class GetPopularMoviesUseCase(
    private val moviesRepository: IMoviesRepository,
    private val loadingPopularMoviesUseCase: LoadPopularMoviesUseCase
) : IUseCase.SingleInOut<ResultMutableLiveData<Any>, PagedLiveData<MovieEntity>> {

    override suspend fun execute(data: ResultMutableLiveData<Any>): PagedLiveData<MovieEntity> {
        val dataSourceFactory = moviesRepository.getPopularMoviesDataSource()
        val pageLiveData = LivePagedListBuilder(dataSourceFactory, 20)
        pageLiveData.setBoundaryCallback(PopularMoviesBoundary(data, loadingPopularMoviesUseCase))
        return pageLiveData.build()
    }

    class PopularMoviesBoundary(
        private var loadingLiveData: ResultMutableLiveData<Any>?,
        private var remoteUseCase: LoadPopularMoviesUseCase?
    ) : PagedList.BoundaryCallback<MovieEntity>(), ICoroutinesManager {
        override fun onZeroItemsLoaded() = fetchDataFromNetwork()
        override fun onItemAtEndLoaded(itemAtEnd: MovieEntity) = fetchDataFromNetwork()

        private fun fetchDataFromNetwork() = launchOnUI {
            loadingLiveData?.value = loadingResult()
            loadingLiveData?.postValue(doWithAsync {
                remoteUseCase?.execute()
            })
        }

        fun clear() {
            loadingLiveData = null
            remoteUseCase = null
        }
    }
}