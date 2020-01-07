package com.rasalexman.puremvvm.presentation.tabs.search

import androidx.lifecycle.*
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.applyForType
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.presentation.viewModels.BaseSaveStateViewModel
import com.rasalexman.coroutinesmanager.CoroutinesProvider
import com.rasalexman.puremvvm.domain.GetSearchDataSource

class SearchViewModel(savedState: SavedStateHandle) : BaseSaveStateViewModel(savedState) {

    private val getSearchRemoteDataSource: GetSearchDataSource by immutableInstance()

    private val queryLiveData by unsafeLazy {
        MutableLiveData<SEvent.FetchWith<String>>()
    }

    override val resultLiveData by unsafeLazy {
        MutableLiveData<SResult<Boolean>>()
    }

    override val anyLiveData by unsafeLazy {
        queryLiveData.switchMap { event ->
            liveData(viewModelScope.coroutineContext + CoroutinesProvider.IO) {
                emitSource(getSearchRemoteDataSource.execute(event.data, resultLiveData))
            }
        }
    }

    override fun processViewEvent(viewEvent: SEvent) {
        viewEvent.applyForType(queryLiveData::setValue)
    }
}