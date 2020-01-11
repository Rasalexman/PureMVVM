package com.rasalexman.tabevents.presentation.upcoming

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.applyForType
import com.rasalexman.core.common.extensions.loadingResult
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.FetchWith
import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.presentation.viewModels.BaseSaveStateViewModel
import com.rasalexman.coroutinesmanager.CoroutinesProvider
import com.rasalexman.tabevents.domain.GetUpcomingMoviesUseCase

class UpcomingViewModel(saveState: SavedStateHandle) : BaseSaveStateViewModel(saveState) {

    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase by immutableInstance()

    private val pageLiveData by unsafeLazy { savedStateHandler.getLiveData(KEY_UPCOMING_PAGE, 1) }

    override val resultLiveData by unsafeLazy {
        pageLiveData.switchMap { page ->
            liveData(viewModelScope.coroutineContext + CoroutinesProvider.IO) {
                emit(loadingResult())
                emit(getUpcomingMoviesUseCase.execute(page))
            }
        }
    }

    override fun processViewEvent(viewEvent: SEvent) {
        viewEvent.applyForType<FetchWith<Int>> {
            val pageValue = pageLiveData.value ?: it.data
            pageLiveData.value = (pageValue + 1)
        }
    }

    companion object {
        const val KEY_UPCOMING_PAGE = "KEY_UPCOMING_PAGE"
    }
}