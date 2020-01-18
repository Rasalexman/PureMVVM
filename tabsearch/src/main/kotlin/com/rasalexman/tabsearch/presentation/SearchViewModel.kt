package com.rasalexman.tabsearch.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagedList
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.onEvent
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.FetchWith
import com.rasalexman.core.presentation.viewModels.BaseSaveStateViewModel
import com.rasalexman.models.ui.MovieItemUI
import com.rasalexman.tabsearch.domain.IGetSearchDataSource

class SearchViewModel(savedState: SavedStateHandle) : BaseSaveStateViewModel(savedState) {

    private val getSearchRemoteDataSource: IGetSearchDataSource by immutableInstance()

    override val resultLiveData by unsafeLazy {
        onEvent<FetchWith<String>, PagedList<MovieItemUI>> {
            emitSource(getSearchRemoteDataSource.execute(it.data, errorLiveData))
        }
    }
}