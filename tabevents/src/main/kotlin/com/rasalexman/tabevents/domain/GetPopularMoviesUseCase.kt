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
import com.rasalexman.tabevents.BuildConfig

class GetPopularMoviesUseCase(
    private val moviesRepository: IMoviesRepository,
    private val loadingPopularMoviesUseCase: LoadPopularMoviesUseCase,
    private val getPopularMoviesPageCountUseCase: GetPopularMoviesPageCountUseCase
) : IUseCase.SingleInOut<ResultMutableLiveData<Any>, PagedLiveData<MovieEntity>> {

    override suspend fun execute(data: ResultMutableLiveData<Any>): PagedLiveData<MovieEntity> {
        val dataSourceFactory = moviesRepository.getPopularMoviesDataSource()
        val pageLiveData = LivePagedListBuilder(dataSourceFactory, BuildConfig.ITEMS_ON_PAGE)
        val popularMoviesCount = getPopularMoviesPageCountUseCase.execute()
        pageLiveData.setBoundaryCallback(PopularMoviesBoundary(data, loadingPopularMoviesUseCase, popularMoviesCount))
        return pageLiveData.build()
    }

    class PopularMoviesBoundary(
        private var loadingLiveData: ResultMutableLiveData<Any>?,
        private var remoteUseCase: LoadPopularMoviesUseCase?,
        currentPage: Int
    ) : PagedList.BoundaryCallback<MovieEntity>(), ICoroutinesManager {
        private var page: Int = currentPage

        override fun onZeroItemsLoaded() = fetchDataFromNetwork()
        override fun onItemAtEndLoaded(itemAtEnd: MovieEntity) = fetchDataFromNetwork()

        private fun fetchDataFromNetwork() = launchOnUI {
            loadingLiveData?.value = loadingResult()
            loadingLiveData?.postValue(doWithAsync {
                remoteUseCase?.execute(page++)
            })
        }

        fun clear() {
            loadingLiveData = null
            remoteUseCase = null
        }
    }
}