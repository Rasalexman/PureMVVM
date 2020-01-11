package com.rasalexman.core.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mincor.kodi.core.IKodi
import com.mincor.kodi.delegates.immutableGetter
import com.rasalexman.core.common.extensions.errorResult
import com.rasalexman.core.data.dto.NavigateTo
import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.SuspendCatch
import com.rasalexman.coroutinesmanager.SuspendTry
import com.rasalexman.coroutinesmanager.launchOnUITryCatch

open class BaseViewModel : ViewModel(), IKodi,
    IBaseViewModel {

    protected open val anyLiveData: LiveData<*>? = null
    protected open val resultLiveData: LiveData<*>? = null
    protected open val errorLiveData by immutableGetter { MutableLiveData<SResult<*>>() }

    protected open val defaultCatchBlock: SuspendCatch<Unit> = {
        errorLiveData.value = errorResult(message = it.message.orEmpty(), exception = it)
    }

    /**
     * Base Function to add to ViewStateHandler Some Values by key [String]
     */
    override fun <T : Any> addToSaveState(key: String, value: T) = Unit

    /**
     * Base Function fro getting value from ViewStateHandler by key [String]
     */
    override fun <T : Any> getFromSaveState(key: String): T? = null

    /**
     * Process [SEvent] from Presentation View Controller (such as Activity or Fragment) to ViewModel
     *
     * @param viewEvent [SEvent] - any implementation to handler with this fragment
     */
    override fun processViewEvent(viewEvent: SEvent) {
        when (viewEvent) {
            is NavigateTo -> {
                val (direction, navigator) = viewEvent
                navigator.navigate(direction)
            }
        }
    }

    /**
     *
     */
    override fun onAnyLiveData(): LiveData<*>? = anyLiveData

    /**
     *
     */
    override fun onResultLiveData(): LiveData<*>? = resultLiveData

    /**
     *
     */
    override fun onErrorLiveData(): LiveData<SResult<*>> = errorLiveData

    /**
     *
     */
    fun ICoroutinesManager.launchOnUIDefault(tryBlock: SuspendTry<Unit>) =
        launchOnUITryCatch(tryBlock, defaultCatchBlock)
}
