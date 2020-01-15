package com.rasalexman.tabhome.domain.genres.impl

import com.rasalexman.core.common.extensions.mapListTo
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.data.repository.IGenresRepository
import com.rasalexman.models.ui.GenreItemUI
import com.rasalexman.tabhome.domain.genres.IGetLocalGenresUseCase

internal class GetLocalGenresUseCase(
    private val repository: IGenresRepository
) : IGetLocalGenresUseCase {
    override suspend fun execute(): ResultList<GenreItemUI> =
        repository.getLocalGenresList().mapListTo()
}