package com.rasalexman.tabevents.presentation.toprated

import androidx.lifecycle.SavedStateHandle
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.*
import com.rasalexman.core.data.dto.FetchWith
import com.rasalexman.core.presentation.viewModels.BaseSaveStateViewModel
import com.rasalexman.tabevents.domain.IGetTopRatedMoviesUseCase

class TopRatedViewModel(saveState: SavedStateHandle) : BaseSaveStateViewModel(saveState) {

    private val getTopRatedViewModel: IGetTopRatedMoviesUseCase by immutableInstance()

    init {
        processViewEvent(START_PAGE.toFetch())
    }

    override val resultLiveData by unsafeLazy {
        onEventResult<FetchWith<Int>> {
            emit(loadingResult())
            val currentPage = savedStateHandler.getCurrentPage(KEY_TOP_RATED_PAGE, it.data)
            emit(getTopRatedViewModel.execute(currentPage))
        }
    }

    companion object {
        private const val START_PAGE = 1
        private const val KEY_TOP_RATED_PAGE = "KEY_TOP_RATED_PAGE"
    }
}