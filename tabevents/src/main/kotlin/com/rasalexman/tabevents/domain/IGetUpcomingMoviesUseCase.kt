package com.rasalexman.tabevents.domain

import com.rasalexman.core.domain.IUseCase
import com.rasalexman.models.ui.MovieItemUI

interface IGetUpcomingMoviesUseCase : IUseCase.SingleInOutList<Int, MovieItemUI>