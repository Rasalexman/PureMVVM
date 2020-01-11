package com.rasalexman.tabhome.presentation.movieslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.applyForType
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.FetchWith
import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.coroutinesmanager.CoroutinesProvider
import com.rasalexman.tabhome.domain.movies.GetMoviesDataSourceUseCase

class MoviesViewModel : BaseViewModel() {

    private val getMoviesDataSourceUseCase: GetMoviesDataSourceUseCase by immutableInstance()

    private val genreIdLiveData by unsafeLazy { MutableLiveData<FetchWith<Int>>() }
    override val resultLiveData by unsafeLazy { MutableLiveData<SResult<Any>>() }

    override val anyLiveData by unsafeLazy {
        genreIdLiveData.switchMap {
            liveData(viewModelScope.coroutineContext + CoroutinesProvider.IO) {
                emitSource(getMoviesDataSourceUseCase.execute(it.data, resultLiveData))
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