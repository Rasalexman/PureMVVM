package com.rasalexman.tabhome.presentation.movieslist

import androidx.paging.PagedList
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.onEvent
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.FetchWith
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.models.ui.MovieItemUI
import com.rasalexman.tabhome.domain.movies.IGetMoviesDataSourceUseCase

class MoviesViewModel : BaseViewModel() {

    private val getMoviesDataSourceUseCase: IGetMoviesDataSourceUseCase by immutableInstance()

    override val resultLiveData by unsafeLazy {
        onEvent<FetchWith<Int>, PagedList<MovieItemUI>> {
            emitSource(getMoviesDataSourceUseCase.execute(it.data, errorLiveData))
        }
    }

    override fun onCleared() {
        getMoviesDataSourceUseCase.clearBoundaries()
        super.onCleared()
    }
}