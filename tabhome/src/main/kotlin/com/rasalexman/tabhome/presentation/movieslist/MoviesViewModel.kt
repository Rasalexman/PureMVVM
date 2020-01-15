package com.rasalexman.tabhome.presentation.movieslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.paging.PagedList
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.applyForType
import com.rasalexman.core.common.extensions.asyncLiveData
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.FetchWith
import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.models.ui.MovieItemUI
import com.rasalexman.tabhome.domain.movies.IGetMoviesDataSourceUseCase

class MoviesViewModel : BaseViewModel() {

    private val getMoviesDataSourceUseCase: IGetMoviesDataSourceUseCase by immutableInstance()

    private val genreIdLiveData by unsafeLazy { MutableLiveData<FetchWith<Int>>() }
    override val resultLiveData by unsafeLazy { MutableLiveData<SResult<Any>>() }

    override val anyLiveData by unsafeLazy {
        genreIdLiveData.switchMap { event ->
            asyncLiveData<PagedList<MovieItemUI>> {
                emitSource(getMoviesDataSourceUseCase.execute(event.data, resultLiveData))
            }
        }
    }

    override fun onCleared() {
        getMoviesDataSourceUseCase.clearBoundaries()
        super.onCleared()
    }

    override fun processViewEvent(viewEvent: SEvent) {
        viewEvent.applyForType(genreIdLiveData::setValue)
    }
}