package com.rasalexman.providers.network.responses

import androidx.annotation.Keep
import retrofit2.Response

typealias QuasaResponse<T> = Response<ServerResponse<T>>

@Keep
data class ServerResponse<T>(val data: T)
