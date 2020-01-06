package com.rasalexman.tabhome.domain.genres

import com.rasalexman.core.common.extensions.applyIfSuccessSuspend
import com.rasalexman.core.common.extensions.mapListBy
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.providers.data.repository.IGenresRepository
import com.rasalexman.tabhome.data.convert
import com.rasalexman.tabhome.presentation.genreslist.GenreItemUI

class GetRemoteGenresUseCase(
    private val repository: IGenresRepository
) : IUseCase.Out<SResult<List<GenreItemUI>>> {
    override suspend fun execute(): SResult<List<GenreItemUI>> {
        return repository
            .getRemoteGenresList()
            .applyIfSuccessSuspend(repository::saveGenresList)
            .mapListBy { convert() }
    }
}