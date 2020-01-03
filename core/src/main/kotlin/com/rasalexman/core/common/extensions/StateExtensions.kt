package com.rasalexman.core.common.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.rasalexman.core.common.typealiases.UnitHandler
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.data.errors.QException
import com.rasalexman.core.data.base.IConvertableTo

// /------ ViewResult extensions
inline fun <reified T : Any> Any.successResult(data: T): SResult<T> = SResult.Success(data)
inline fun <reified T : Any> Any.loadingResult(): SResult<T> = SResult.Loading
inline fun <reified T : Any> Any.emptyResult(): SResult<T> = SResult.Empty
inline fun <reified T : Any> Any.defaultResult(): SResult<T> = SResult.Default
inline fun <reified T : Any> Any.clearResult(): SResult<T> = SResult.Clear

fun Any.navigateToResult(
    to: NavDirections,
    navigator: NavController
) = SResult.NavigateResult.NavigateTo(to, navigator)

fun Any.navigateBackResult() =
    SResult.NavigateResult.NavigateBack

fun Any.errorResult(
    message: String = "",
    code: Int = -1,
    exception: Throwable? = null
) = SResult.ErrorResult.Error(message, code, exception)

fun Any.alertResult(
    message: Any? = null,
    exception: Throwable? = null,
    okHandler: UnitHandler? = null
) = SResult.ErrorResult.Alert(message, okHandler, exception)

// /-------- toState Convertables
inline fun <reified T : Any> T.toSuccessResult(): SResult<T> =
    successResult(this)

inline fun <reified T : NavDirections> T.toNavResult(
    navigator: NavController
) = navigateToResult(this, navigator)

inline fun <reified T : Throwable> T.toErrorResult() =
    errorResult(this.message ?: this.cause?.message.orEmpty(), 0, this)

inline fun <reified T : Throwable> T.toAlertResult() =
    alertResult(message = this.message ?: this.cause?.message.orEmpty(), exception = this)

// /-------- HANDLE FUNCTION
fun SResult<*>.handle() {
    if (isNeedHandle) isHandled = true
}

// /------- Mapper function
inline fun <reified O : Any, reified I : IConvertableTo<O>> SResult<List<I>>.mapListTo(): SResult<List<O>> {
    return when (this) {
        is SResult.Success -> {
            data.mapNotNull { it.convertTo() }.toSuccessResult()
        }
        else -> this as SResult<List<O>>
    }
}

inline fun <reified O : Any, reified I : IConvertableTo<O>> SResult<I>.mapTo(): SResult<*> {
    return when (this) {
        is SResult.Success -> {
            this.data.convertTo()?.run {
                this.toSuccessResult()
            } ?: emptyResult<Any>()
        }
        else -> this
    }
}

fun SResult.ErrorResult.getMessage(): Any? {
    return (this.message?.takeIf { (it as? String)?.isNotEmpty() == true || (it as? Int) != null } ?: (this.exception as? QException)?.getErrorMessageResId()) ?: this.exception?.message ?: this.exception?.cause?.message
}

// /--- Inline Applying functions
inline fun <reified I : Any> SResult<I>.applyIfSuccess(block: (I) -> Unit): SResult<I> {
    if (this is SResult.Success) block(this.data)
    return this
}

@Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
suspend inline fun <reified I : Any> SResult<I>.applyIfSuccessSuspend(crossinline block: suspend (I) -> Unit): SResult<I> {
    if (this is SResult.Success) block(this.data)
    return this
}

inline fun <reified I : SResult<*>> SResult<*>.applyIfType(block: I.() -> Unit): SResult<*> {
    if (this::class == I::class) block(this as I)
    return this
}

@Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
suspend inline fun <reified I : SResult<*>> SResult<*>.applyIfTypeSuspend(crossinline block: suspend I.() -> Unit): SResult<*> {
    if (this::class == I::class) block(this as I)
    return this
}

inline fun <reified I : SResult<*>> SResult<*>.mapIfType(block: I.() -> SResult<*>): SResult<*> {
    return if (this::class == I::class) block(this as I)
    else this
}

suspend inline fun <reified I : SResult<*>> SResult<*>.mapIfTypeSuspend(crossinline block: suspend I.() -> SResult<*>): SResult<*> {
    return if (this::class == I::class) block(this as I)
    else this
}
