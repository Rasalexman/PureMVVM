package com.rasalexman.tabevents.presentation.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.applyForType
import com.rasalexman.core.common.extensions.asyncLiveData
import com.rasalexman.core.common.extensions.loadingResult
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.Refresh
import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.coroutinesmanager.CoroutinesProvider
import com.rasalexman.models.ui.MovieItemUI
import com.rasalexman.tabevents.domain.IGetPopularMoviesUseCase
import com.rasalexman.tabevents.domain.ILoadPopularMoviesUseCase
import kotlinx.coroutines.launch

class PopularViewModel : BaseViewModel() {

    private val getPopularMoviesUseCase: IGetPopularMoviesUseCase by immutableInstance()
    private val refreshPopularMoviesUseCase: ILoadPopularMoviesUseCase by immutableInstance()

    override val resultLiveData by unsafeLazy { MutableLiveData<SResult<Any>>() }

    override val anyLiveData by unsafeLazy {
        asyncLiveData<PagedList<MovieItemUI>> {
            resultLiveData.postValue(loadingResult())
            emitSource(getPopularMoviesUseCase.execute(resultLiveData))
        }
    }

    override fun onCleared() {
        getPopularMoviesUseCase.clearBoundaries()
        super.onCleared()
    }

    override fun processViewEvent(viewEvent: SEvent) {
        // Swipe to refresh event
        viewEvent.applyForType(::refreshContent)
    }

    private fun refreshContent(viewEvent: Refresh) {
        viewModelScope.launch(viewModelScope.coroutineContext + CoroutinesProvider.IO) {
            resultLiveData.postValue(refreshPopularMoviesUseCase.execute(1))
        }
    }
}