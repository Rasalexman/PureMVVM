package com.rasalexman.core.data.dto

import androidx.navigation.NavController
import androidx.navigation.NavDirections

sealed class SEvent {
    object Fetch : SEvent()
    data class FetchWith<T : Any>(val data: T) : SEvent()
    object Refresh : SEvent()

    data class NavigateTo(
        val navDirection: NavDirections,
        val qNavigator: NavController
    ) : SEvent()

    sealed class SEventWithData<out T : Any>(val data: T) : SEvent() {
        class Save<T : Any>(dataToSave: T) : SEventWithData<T>(dataToSave)
        class Update<T : Any>(dataToUpdate: T) : SEventWithData<T>(dataToUpdate)
        class Delete<T : Any>(dataToDelete: T) : SEventWithData<T>(dataToDelete)
    }
}