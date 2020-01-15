package com.rasalexman.tabhome.domain.movies

import com.rasalexman.core.common.typealiases.PagedLiveData
import com.rasalexman.core.common.typealiases.ResultMutableLiveData
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.models.ui.MovieItemUI

interface IGetMoviesDataSourceUseCase : IUseCase.DoubleInOut<Int, ResultMutableLiveData<Any>, PagedLiveData<MovieItemUI>> {
    fun clearBoundaries()
}