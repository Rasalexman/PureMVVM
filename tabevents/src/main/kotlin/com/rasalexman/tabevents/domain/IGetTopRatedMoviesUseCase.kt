package com.rasalexman.tabevents.domain

import com.rasalexman.core.domain.IUseCase
import com.rasalexman.models.ui.MovieItemUI

interface IGetTopRatedMoviesUseCase : IUseCase.SingleInOutList<Int, MovieItemUI>