package com.rasalexman.tabsearch.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.switchMap
import androidx.paging.PagedList
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.applyForType
import com.rasalexman.core.common.extensions.asyncLiveData
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.FetchWith
import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.presentation.viewModels.BaseSaveStateViewModel
import com.rasalexman.models.ui.MovieItemUI
import com.rasalexman.tabsearch.domain.IGetSearchDataSource

class SearchViewModel(savedState: SavedStateHandle) : BaseSaveStateViewModel(savedState) {

    private val getSearchRemoteDataSource: IGetSearchDataSource by immutableInstance()

    private val queryLiveData by unsafeLazy {
        MutableLiveData<FetchWith<String>>()
    }

    override val resultLiveData by unsafeLazy {
        MutableLiveData<SResult<Boolean>>()
    }

    override val anyLiveData by unsafeLazy {
        queryLiveData.switchMap { event ->
            asyncLiveData<PagedList<MovieItemUI>> {
                emitSource(getSearchRemoteDataSource.execute(event.data, resultLiveData))
            }
        }
    }

    override fun processViewEvent(viewEvent: SEvent) {
        viewEvent.applyForType(queryLiveData::setValue)
    }
}