package com.rasalexman.data.source.remote

import androidx.paging.PageKeyedDataSource
import com.rasalexman.core.common.extensions.anyResult
import com.rasalexman.core.common.extensions.applyIfSuccessSuspend
import com.rasalexman.core.common.extensions.loadingResult
import com.rasalexman.core.common.extensions.mapIfSuccessSuspend
import com.rasalexman.core.common.typealiases.AnyResultMutableLiveData
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.doWithTryCatchAsync
import com.rasalexman.coroutinesmanager.launchOnUI
import com.rasalexman.models.remote.MovieModel
import com.rasalexman.providers.network.api.IMovieApi
import com.rasalexman.providers.network.handlers.errorResultCatchBlock
import com.rasalexman.providers.network.responses.GetMoviesListResponse
import com.rasalexman.providers.network.responses.getResult

class SearchRemoteDataSource(
    private val movieApi: IMovieApi,
    private val query: String,
    private val resultLiveData: AnyResultMutableLiveData
) : PageKeyedDataSource<Int, MovieModel>(), ICoroutinesManager {

    private var currentPage = 1
    private var maxPages = 1000

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieModel>
    ) = launchOnUI {
        resultLiveData.postValue(
            loadMoviesByQuery(query).mapIfSuccessSuspend {
                callback.onResult(this, currentPage - 1, currentPage)
                anyResult()
            }
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) =
        launchOnUI {
            resultLiveData.postValue(
                loadMoviesByQuery(query).applyIfSuccessSuspend {
                    callback.onResult(it, currentPage)
                }
            )
        }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) = Unit

    private suspend fun loadMoviesByQuery(query: String): SResult<List<MovieModel>> =
        doWithTryCatchAsync(
            tryBlock = {
                resultLiveData.postValue(loadingResult())
                movieApi.getSearchMovies(query).getResult {
                    changePage(it)
                    it.results
                }
            }, catchBlock = errorResultCatchBlock()
        )

    /**
     * Increase currentPage count if available
     *
     * @param response - current request response data
     */
    private fun changePage(response: GetMoviesListResponse<MovieModel>) {
        val currentPage = response.page
        maxPages = response.total_pages
        if (currentPage < maxPages) this.currentPage++
    }
}