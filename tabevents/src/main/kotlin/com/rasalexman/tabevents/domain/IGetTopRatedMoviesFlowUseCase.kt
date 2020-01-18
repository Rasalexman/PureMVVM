package com.rasalexman.tabevents.domain

import androidx.lifecycle.LiveData
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.models.ui.MovieItemUI

interface IGetTopRatedMoviesFlowUseCase : IUseCase.SingleFlowInOutList<LiveData<Int>, MovieItemUI>