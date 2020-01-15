package com.rasalexman.tabhome.domain.movies

import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.models.local.MovieEntity

interface IGetRemoteMoviesByGenreIdUseCase : IUseCase.DoubleInOut<Int, Long?, ResultList<MovieEntity>>