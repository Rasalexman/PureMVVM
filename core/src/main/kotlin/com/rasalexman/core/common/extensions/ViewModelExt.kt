package com.rasalexman.core.common.extensions

import androidx.lifecycle.*
import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.core.presentation.viewModels.IBaseViewModel
import com.rasalexman.coroutinesmanager.CoroutinesProvider

/**
 *
 */
inline fun<reified T> BaseViewModel.asyncLiveData(noinline block: suspend LiveDataScope<T>.() -> Unit) =
    liveData(context = viewModelScope.coroutineContext + CoroutinesProvider.IO, block = block)

/**
 *
 */
inline fun<reified E : SEvent> BaseViewModel.onEventResult(crossinline block: suspend LiveDataScope<SResult<*>>.(E) -> Unit): LiveData<SResult<*>> {
    return eventLiveData.switchMap {
        asyncLiveData<SResult<*>> {
            it.applyForType<E> { this.block(it) }
        }
    }
}

/**
 *
 */
inline fun<reified E : SEvent, reified T : Any> BaseViewModel.onEvent(crossinline block: suspend LiveDataScope<T>.(E) -> Unit): LiveData<T> {
    return eventLiveData.switchMap {
        asyncLiveData<T> {
            it.applyForType<E> { this.block(it) }
        }
    }
}

fun SavedStateHandle.getCurrentPage(key: String, latPage: Int): Int {
    val savedPage = this.get<Int>(key)?.plus(1)
    val currentPage = savedPage?.takeIf { it > latPage } ?: latPage
    this.set(key, currentPage)
    return currentPage
}


/**
 * Process [SEvent] from Presentation View Controller (such as Activity or Fragment) to ViewModel
 *
 * @param viewEvent [SEvent] - any implementation to handler with this fragment
 */
fun IBaseViewModel.processViewEvent(viewEvent: SEvent) {
    eventLiveData.value = viewEvent
}