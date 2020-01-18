package com.rasalexman.tabevents.presentation.upcoming

import androidx.lifecycle.SavedStateHandle
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.*
import com.rasalexman.core.data.dto.FetchWith
import com.rasalexman.core.presentation.viewModels.BaseSaveStateViewModel
import com.rasalexman.tabevents.domain.IGetUpcomingMoviesUseCase

class UpcomingViewModel(saveState: SavedStateHandle) : BaseSaveStateViewModel(saveState) {

    private val getUpcomingMoviesUseCase: IGetUpcomingMoviesUseCase by immutableInstance()


    init {
        processViewEvent(START_PAGE.toFetch())
    }

    override val resultLiveData by unsafeLazy {
        onEventResult<FetchWith<Int>> {
            emit(loadingResult())
            val currentPage = savedStateHandler.getCurrentPage(KEY_UPCOMING_PAGE, it.data)
            emit(getUpcomingMoviesUseCase.execute(currentPage))
        }
    }

    companion object {
        private const val START_PAGE = 1
        private const val KEY_UPCOMING_PAGE = "KEY_UPCOMING_PAGE"
    }
}