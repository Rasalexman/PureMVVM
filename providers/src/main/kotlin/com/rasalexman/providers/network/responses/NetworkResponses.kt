package com.rasalexman.providers.network.responses

import androidx.annotation.Keep
import com.rasalexman.core.common.extensions.emptyResult
import com.rasalexman.core.common.extensions.errorResult
import com.rasalexman.core.common.extensions.successResult
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.providers.data.models.remote.GenreModel
import com.rasalexman.providers.data.models.remote.MovieModel
import retrofit2.Response

@Keep
data class GetGenresResponse(val genres: List<GenreModel>)

data class GetMoviesListResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<MovieModel>
)


inline fun <reified T : Any, reified O : Any> Response<T>.getResult(alsoAction: (T) -> O): SResult<O> {
    return this.body()?.run {
        successResult(alsoAction(this))
    } ?: this.errorBody()?.let {
        errorResult(this.message(), this.code())
    } ?: emptyResult()
}
