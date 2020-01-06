package com.rasalexman.tabhome.presentation.movieslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mincor.kodi.core.immutableInstance
import com.mincor.kodi.core.instance
import com.rasalexman.core.common.extensions.applyForType
import com.rasalexman.core.common.extensions.loadingResult
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.coroutinesmanager.CoroutinesProvider
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.doWithAsync
import com.rasalexman.coroutinesmanager.launchOnUI
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.tabhome.BuildConfig
import com.rasalexman.tabhome.domain.movies.GetLocalMoviesDataSourceUseCase
import com.rasalexman.tabhome.domain.movies.GetRemoteMoviesByGenreIdUseCase

class MoviesViewModel : BaseViewModel() {

    private val localDataSourceUseCase: GetLocalMoviesDataSourceUseCase by immutableInstance()
    private var moviesBoundaryCallback: MoviesBoundaryCallback? = null

    private val genreIdLiveData by unsafeLazy { MutableLiveData<SEvent.FetchWith<Int>>() }
    override val resultLiveData by unsafeLazy { MutableLiveData<SResult<Any>>() }

    override val anyLiveData by unsafeLazy {
        genreIdLiveData.switchMap {
            liveData(viewModelScope.coroutineContext + CoroutinesProvider.IO) {
                val pageLiveData =
                    LivePagedListBuilder(
                        localDataSourceUseCase.execute(it.data),
                        BuildConfig.ITEMS_ON_PAGE
                    ).setBoundaryCallback(
                        MoviesBoundaryCallback(
                            it.data,
                            resultLiveData,
                            instance()
                        ).also {
                            moviesBoundaryCallback = it
                        }
                    ).build()
                emitSource(pageLiveData)
            }
        }
    }

    override fun processViewEvent(viewEvent: SEvent) {
        viewEvent.applyForType(genreIdLiveData::setValue)
    }

    override fun onCleared() {
        moviesBoundaryCallback?.clear()
        moviesBoundaryCallback = null
        super.onCleared()
    }

    class MoviesBoundaryCallback(
        private val genreId: Int,
        private var loadingLiveData: MutableLiveData<SResult<Any>>?,
        private var remoteUseCase: GetRemoteMoviesByGenreIdUseCase?
    ) : PagedList.BoundaryCallback<MovieEntity>(), ICoroutinesManager {

        override fun onZeroItemsLoaded() = fetchDataFromNetwork()
        override fun onItemAtEndLoaded(itemAtEnd: MovieEntity) = fetchDataFromNetwork()

        private fun fetchDataFromNetwork() = launchOnUI {
            loadingLiveData?.value = loadingResult()
            doWithAsync {
                remoteUseCase?.execute(genreId)
            }
        }

        fun clear() {
            loadingLiveData = null
            remoteUseCase = null
        }
    }
}