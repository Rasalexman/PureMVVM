package com.rasalexman.core.presentation.viewModels

import androidx.lifecycle.LiveData
import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.data.dto.SResult

interface IBaseViewModel {
    fun processViewEvent(viewEvent: SEvent)

    fun onResultLiveData(): LiveData<*>?
    fun onErrorLiveData(): LiveData<SResult<Any>>

    fun <T : Any> addToSaveState(key: String, value: T)
    fun <T : Any> getFromSaveState(key: String): T?
}