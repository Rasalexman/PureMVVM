package com.rasalexman.tabhome.domain.genres

import com.rasalexman.core.common.extensions.mapListTo
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.models.ui.GenreItemUI
import com.rasalexman.providers.data.repository.IGenresRepository

class GetLocalGenresUseCase(
    private val repository: IGenresRepository
) : IUseCase.Out<SResult<List<GenreItemUI>>> {
    override suspend fun execute(): SResult<List<GenreItemUI>> =
        repository.getLocalGenresList().mapListTo()
}