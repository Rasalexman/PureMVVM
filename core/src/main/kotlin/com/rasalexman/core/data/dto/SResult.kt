package com.rasalexman.core.data.dto

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.rasalexman.core.common.typealiases.UnitHandler

sealed class SResult<out T : Any> {

    open var isNeedHandle: Boolean = false
    open var isHandled: Boolean = false

    //---- All Custom View States
    class Success<out T : Any>(
        val data: T
    ) : SResult<T>()

    object AnySResult : SResult<Any>()
    object Loading : SResult<Nothing>()
    object Empty : SResult<Nothing>()
    object Default : SResult<Nothing>() {
        override var isHandled: Boolean = true
    }

    object Clear : SResult<Nothing>()

    //---- Navigation States
    sealed class NavigateResult : SResult<Nothing>() {
        override var isNeedHandle = true

        data class NavigateTo(
            val navDirection: NavDirections,
            val qNavigator: NavController? = null
        ) : NavigateResult()

        object NavigateBack : NavigateResult()
    }

    //---- Error States
    sealed class ErrorResult : SResult<Nothing>() {
        open val message: Any? = null
        open val exception: Throwable? = null
        override var isNeedHandle = true

        data class Error(
            override val message: Any?,
            val code: Int,
            override val exception: Throwable? = null
        ) : ErrorResult()

        data class Alert(
            override val message: Any? = null,
            val okHandler: UnitHandler? = null,
            override val exception: Throwable? = null
        ) : ErrorResult()
    }
}
