package com.rasalexman.tabevents.presentation.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.applyForType
import com.rasalexman.core.common.extensions.loadingResult
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.coroutinesmanager.CoroutinesProvider
import com.rasalexman.tabevents.domain.GetPopularMoviesUseCase
import com.rasalexman.tabevents.domain.LoadPopularMoviesUseCase
import kotlinx.coroutines.launch

class PopularViewModel : BaseViewModel() {

    private val getPopularMoviesUseCase: GetPopularMoviesUseCase by immutableInstance()
    private val loadPopularMoviesUseCase: LoadPopularMoviesUseCase by immutableInstance()

    override val resultLiveData by unsafeLazy { MutableLiveData<SResult<Any>>() }

    override val anyLiveData by unsafeLazy {
        liveData(viewModelScope.coroutineContext + CoroutinesProvider.IO) {
            resultLiveData.postValue(loadingResult())
            emitSource(getPopularMoviesUseCase.execute(resultLiveData))
        }
    }

    override fun processViewEvent(viewEvent: SEvent) {
        // Swipe to refresh event
        viewEvent.applyForType<SEvent.Refresh> {
            viewModelScope.launch(viewModelScope.coroutineContext + CoroutinesProvider.IO) {
                resultLiveData.postValue(loadPopularMoviesUseCase.execute(1))
            }
        }
    }
}