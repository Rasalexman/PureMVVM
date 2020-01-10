package com.rasalexman.puremvvm.data.source.remote

import androidx.paging.PageKeyedDataSource
import com.rasalexman.core.common.extensions.*
import com.rasalexman.core.common.typealiases.ResultMutableLiveData
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.doWithTryCatchAsync
import com.rasalexman.coroutinesmanager.launchOnUI
import com.rasalexman.models.remote.MovieModel
import com.rasalexman.providers.network.api.IMovieApi
import com.rasalexman.providers.network.responses.GetMoviesListResponse
import com.rasalexman.providers.network.responses.getResult

class SearchRemoteDataSource(
    private val movieApi: IMovieApi,
    private val query: String,
    private val resultLiveData: ResultMutableLiveData<Boolean>
) : PageKeyedDataSource<Int, MovieModel>(), ICoroutinesManager {

    private var currentPage = 1
    private var maxPages = 1000

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieModel>
    ) = launchOnUI {
        resultLiveData.postValue(loadingResult())
        loadMoviesByQuery(query).applyIfSuccessSuspend {
            callback.onResult(it, currentPage - 1, currentPage)
        }.applyForType<SResult.ErrorResult> {
            resultLiveData.postValue(it)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) =
        launchOnUI {
            resultLiveData.postValue(loadingResult())
            loadMoviesByQuery(query).applyIfSuccessSuspend {
                callback.onResult(it, currentPage)
            }.applyIfType<SResult.ErrorResult> {
                resultLiveData.postValue(this)
            }
        }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) = Unit

    private suspend fun loadMoviesByQuery(query: String): SResult<List<MovieModel>> =
        doWithTryCatchAsync(
            tryBlock = {
                movieApi.getSearchMovies(query).getResult {
                    changePage(it)
                    it.results
                }
            }, catchBlock = {
                errorResult(message = it.message.orEmpty(), exception = it)
            }
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