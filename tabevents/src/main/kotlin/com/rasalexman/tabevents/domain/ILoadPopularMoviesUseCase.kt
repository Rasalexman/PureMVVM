package com.rasalexman.tabevents.domain

import com.rasalexman.core.domain.IUseCase
import com.rasalexman.models.local.MovieEntity

interface ILoadPopularMoviesUseCase : IUseCase.SingleInOutList<Int, MovieEntity> {
}