package com.rasalexman.tabevents.presentation.toprated

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.*
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.core.data.dto.FetchWith
import com.rasalexman.core.presentation.viewModels.BaseSaveStateViewModel
import com.rasalexman.models.ui.MovieItemUI
import com.rasalexman.tabevents.domain.IGetTopRatedMoviesFlowUseCase

class TopRatedViewModel(saveState: SavedStateHandle) : BaseSaveStateViewModel(saveState) {

    private val getTopRatedViewModelFlow: IGetTopRatedMoviesFlowUseCase by immutableInstance()

    init {
        processViewEvent(START_PAGE.toFetch())
    }

    override val resultLiveData by unsafeLazy {
        asyncLiveData<ResultList<MovieItemUI>> {
            emitSource(getTopRatedViewModelFlow.execute(onEvent<FetchWith<Int>, Int> {
                val currentPage = savedStateHandler.getCurrentPage(KEY_TOP_RATED_PAGE, it.data)
                emit(currentPage)
            }).asLiveData())
        }
    }

    companion object {
        private const val START_PAGE = 1
        private const val KEY_TOP_RATED_PAGE = "KEY_TOP_RATED_PAGE"
    }
}