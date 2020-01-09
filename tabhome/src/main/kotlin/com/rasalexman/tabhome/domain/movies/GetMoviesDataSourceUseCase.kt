package com.rasalexman.tabhome.domain.movies

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rasalexman.core.common.extensions.loadingResult
import com.rasalexman.core.common.typealiases.PagedLiveData
import com.rasalexman.core.common.typealiases.ResultMutableLiveData
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.doWithAsync
import com.rasalexman.coroutinesmanager.launchOnUI
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.providers.data.repository.IMoviesRepository

class GetMoviesDataSourceUseCase(
    private val moviesRepository: IMoviesRepository,
    private var remoteMoviesByGenreIdUseCase: GetRemoteMoviesByGenreIdUseCase
) : IUseCase.DoubleInOut<Int, ResultMutableLiveData<Any>, PagedLiveData<MovieEntity>> {

    private var moviesBoundaryCallback: MoviesBoundaryCallback? = null

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(
        genreId: Int,
        resultLiveData: ResultMutableLiveData<Any>
    ): PagedLiveData<MovieEntity> {
        clearBoundaries()
        resultLiveData.postValue(loadingResult())
        val dataSourceFactory = moviesRepository.getMoviesByGenreDataSource(genreId)
        val pageLiveData = LivePagedListBuilder(dataSourceFactory, 20)
        moviesBoundaryCallback = MoviesBoundaryCallback(genreId, resultLiveData, remoteMoviesByGenreIdUseCase)
        pageLiveData.setBoundaryCallback(moviesBoundaryCallback)
        return pageLiveData.build()
    }

    fun clearBoundaries() {
        moviesBoundaryCallback?.clear()
        moviesBoundaryCallback = null
    }

    class MoviesBoundaryCallback(
        private val genreId: Int,
        private var loadingLiveData: MutableLiveData<SResult<Any>>?,
        private var remoteUseCase: GetRemoteMoviesByGenreIdUseCase?
    ) : PagedList.BoundaryCallback<MovieEntity>(), ICoroutinesManager {

        override fun onZeroItemsLoaded() = fetchDataFromNetwork()
        override fun onItemAtEndLoaded(itemAtEnd: MovieEntity) =
            fetchDataFromNetwork(itemAtEnd.releaseDate)

        private fun fetchDataFromNetwork(fromReleaseDate: Long? = null) = launchOnUI {
            loadingLiveData?.value = loadingResult()
            doWithAsync {
                remoteUseCase?.execute(genreId, fromReleaseDate)
            }
        }

        fun clear() {
            loadingLiveData = null
            remoteUseCase = null
        }
    }

}