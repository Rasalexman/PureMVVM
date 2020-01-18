package com.rasalexman.tabhome.domain.movies.impl

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rasalexman.core.common.extensions.loadingResult
import com.rasalexman.core.common.typealiases.PagedLiveData
import com.rasalexman.core.common.typealiases.ResultMutableLiveData
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.doWithAsync
import com.rasalexman.coroutinesmanager.launchOnUI
import com.rasalexman.data.repository.IMoviesRepository
import com.rasalexman.models.ui.MovieItemUI
import com.rasalexman.tabhome.BuildConfig
import com.rasalexman.tabhome.domain.movies.IGetMoviesDataSourceUseCase
import com.rasalexman.tabhome.domain.movies.IGetRemoteMoviesByGenreIdUseCase

internal class GetMoviesDataSourceUseCase(
    private val moviesRepository: IMoviesRepository,
    private var remoteMoviesByGenreIdUseCase: IGetRemoteMoviesByGenreIdUseCase
) : IGetMoviesDataSourceUseCase {

    private var moviesBoundaryCallback: MoviesBoundaryCallback? = null

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(
        genreId: Int,
        resultLiveData: ResultMutableLiveData<Any>
    ): PagedLiveData<MovieItemUI> {
        resultLiveData.postValue(loadingResult())
        clearBoundaries()
        val dataSourceFactory = moviesRepository.getMoviesByGenreDataSource(genreId).map { it.convertTo() }
        val pageLiveData = LivePagedListBuilder(dataSourceFactory, BuildConfig.ITEMS_ON_PAGE)
        moviesBoundaryCallback =
            MoviesBoundaryCallback(
                genreId,
                resultLiveData,
                remoteMoviesByGenreIdUseCase
            )
        pageLiveData.setBoundaryCallback(moviesBoundaryCallback)
        return pageLiveData.build()
    }

    override fun clearBoundaries() {
        moviesBoundaryCallback?.clear()
        moviesBoundaryCallback = null
    }

    class MoviesBoundaryCallback(
        private val genreId: Int,
        private var loadingLiveData: ResultMutableLiveData<Any>?,
        private var remoteUseCase: IGetRemoteMoviesByGenreIdUseCase?
    ) : PagedList.BoundaryCallback<MovieItemUI>(), ICoroutinesManager {

        override fun onZeroItemsLoaded() = fetchDataFromNetwork()
        override fun onItemAtEndLoaded(itemAtEnd: MovieItemUI) =
            fetchDataFromNetwork(itemAtEnd.originalReleaseDate)

        private fun fetchDataFromNetwork(fromReleaseDate: Long? = null) = launchOnUI {
            loadingLiveData?.value = loadingResult()
            loadingLiveData?.value = doWithAsync {
                remoteUseCase?.execute(genreId, fromReleaseDate)
            }
        }

        fun clear() {
            loadingLiveData = null
            remoteUseCase = null
        }
    }

}