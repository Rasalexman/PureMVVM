package com.rasalexman.tabevents.presentation.popular

import androidx.paging.PagedList
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.asyncLiveData
import com.rasalexman.core.common.extensions.onEventResult
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.Refresh
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.models.ui.MovieItemUI
import com.rasalexman.tabevents.domain.IGetPopularMoviesUseCase
import com.rasalexman.tabevents.domain.ILoadPopularMoviesUseCase

class PopularViewModel : BaseViewModel() {

    private val getPopularMoviesUseCase: IGetPopularMoviesUseCase by immutableInstance()
    private val refreshPopularMoviesUseCase: ILoadPopularMoviesUseCase by immutableInstance()

    override val anyLiveData by unsafeLazy {
        onEventResult<Refresh> {
            emit(refreshPopularMoviesUseCase.execute(0))
        }
    }

    override val resultLiveData by unsafeLazy {
        asyncLiveData<PagedList<MovieItemUI>> {
            emitSource(getPopularMoviesUseCase.execute(errorLiveData))
        }
    }

    override fun onCleared() {
        getPopularMoviesUseCase.clearBoundaries()
        super.onCleared()
    }
}