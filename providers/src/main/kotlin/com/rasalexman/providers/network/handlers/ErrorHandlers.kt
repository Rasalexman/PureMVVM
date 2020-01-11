package com.rasalexman.providers.network.handlers

import com.rasalexman.core.common.extensions.toErrorResult
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.data.errors.QException
import com.rasalexman.coroutinesmanager.SuspendCatch
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

inline fun <reified T : Any> Any.errorResultCatchBlock(): SuspendCatch<SResult<T>> = {
    when (it) {
        is UnknownHostException,
        is IOException -> QException.NoInternetConnectionException().toErrorResult()
        is HttpException -> it.toErrorResult()
        else -> it.toErrorResult()
    }
}