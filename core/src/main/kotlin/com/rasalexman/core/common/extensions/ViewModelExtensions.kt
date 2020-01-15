package com.rasalexman.core.common.extensions

import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rasalexman.coroutinesmanager.CoroutinesProvider

inline fun<reified T> ViewModel.asyncLiveData(noinline block: suspend LiveDataScope<T>.() -> Unit) =
    liveData(context = viewModelScope.coroutineContext + CoroutinesProvider.IO, block = block)