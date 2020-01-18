package com.rasalexman.tabevents.domain.impl

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rasalexman.core.common.extensions.loadingResult
import com.rasalexman.core.common.typealiases.PagedLiveData
import com.rasalexman.core.common.typealiases.ResultMutableLiveData
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.doWithAsync
import com.rasalexman.coroutinesmanager.launchOnUI
import com.rasalexman.data.repository.IMoviesRepository
import com.rasalexman.models.ui.MovieItemUI
import com.rasalexman.tabevents.BuildConfig
import com.rasalexman.tabevents.domain.IGetPopularMoviesPageCountUseCase
import com.rasalexman.tabevents.domain.IGetPopularMoviesUseCase
import com.rasalexman.tabevents.domain.ILoadPopularMoviesUseCase

internal class GetPopularMoviesUseCase(
    private val moviesRepository: IMoviesRepository,
    private val loadingPopularMoviesUseCase: ILoadPopularMoviesUseCase,
    private val getPopularMoviesPageCountUseCase: IGetPopularMoviesPageCountUseCase
) : IGetPopularMoviesUseCase {

    private var popularMoviesBoundary: PopularMoviesBoundary? = null

    override suspend fun execute(data: ResultMutableLiveData<Any>): PagedLiveData<MovieItemUI> {
        data.postValue(loadingResult())
        clearBoundaries()

        val dataSourceFactory = moviesRepository.getPopularMoviesDataSource().map { it.convertTo() }
        val pageLiveData = LivePagedListBuilder(dataSourceFactory, BuildConfig.ITEMS_ON_PAGE)
        val popularMoviesCount = getPopularMoviesPageCountUseCase.execute()
        popularMoviesBoundary =
            PopularMoviesBoundary(
                data,
                loadingPopularMoviesUseCase,
                popularMoviesCount
            )
        pageLiveData.setBoundaryCallback(popularMoviesBoundary)
        return pageLiveData.build()
    }

    override fun clearBoundaries() {
        popularMoviesBoundary?.clear()
        popularMoviesBoundary = null
    }

    class PopularMoviesBoundary(
        private var loadingLiveData: ResultMutableLiveData<Any>?,
        private var remoteUseCase: ILoadPopularMoviesUseCase?,
        currentPage: Int
    ) : PagedList.BoundaryCallback<MovieItemUI>(), ICoroutinesManager {
        private var page: Int = currentPage

        override fun onZeroItemsLoaded() = fetchDataFromNetwork()
        override fun onItemAtEndLoaded(itemAtEnd: MovieItemUI) = fetchDataFromNetwork()

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