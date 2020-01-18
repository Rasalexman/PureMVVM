package com.rasalexman.tabsearch.domain

import com.rasalexman.core.common.typealiases.AnyResultMutableLiveData
import com.rasalexman.core.common.typealiases.PagedLiveData
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.models.ui.MovieItemUI

interface IGetSearchDataSource : IUseCase.DoubleInOut<String, AnyResultMutableLiveData, PagedLiveData<MovieItemUI>>