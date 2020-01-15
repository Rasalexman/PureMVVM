package com.rasalexman.tabhome.domain.genres

import com.rasalexman.core.domain.IUseCase
import com.rasalexman.models.ui.GenreItemUI

interface IGetLocalGenresUseCase : IUseCase.OutList<GenreItemUI>