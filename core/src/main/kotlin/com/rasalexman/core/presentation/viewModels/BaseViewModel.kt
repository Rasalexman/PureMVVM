package com.rasalexman.core.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mincor.kodi.core.IKodi
import com.mincor.kodi.delegates.immutableGetter
import com.rasalexman.core.common.extensions.errorResult
import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.SuspendCatch
import com.rasalexman.coroutinesmanager.SuspendTry
import com.rasalexman.coroutinesmanager.launchOnUITryCatch

open class BaseViewModel : ViewModel(), IKodi,
    IBaseViewModel {

    override val eventLiveData by immutableGetter { MutableLiveData<SEvent>() }

    override val anyLiveData: LiveData<*>? = null
    override val resultLiveData: LiveData<*>? = null
    override val errorLiveData by immutableGetter { MutableLiveData<SResult<*>>() }

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
     *
     */
    fun ICoroutinesManager.launchOnUIDefault(tryBlock: SuspendTry<Unit>) =
        launchOnUITryCatch(tryBlock, defaultCatchBlock)
}
