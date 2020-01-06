package com.rasalexman.core.presentation.viewModels

import androidx.lifecycle.SavedStateHandle

open class BaseSaveStateViewModel(savedState: SavedStateHandle) : BaseViewModel() {

    protected open val savedStateHandler: SavedStateHandle = savedState

    override fun <T : Any> addToSaveState(key: String, value: T) {
        savedStateHandler.set(key, value)
    }

    override fun <T : Any> getFromSaveState(key: String): T? {
        return savedStateHandler[key]
    }
}