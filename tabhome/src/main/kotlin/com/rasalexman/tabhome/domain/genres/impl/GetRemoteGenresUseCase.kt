package com.rasalexman.tabhome.domain.genres.impl

import com.rasalexman.core.common.extensions.applyIfSuccessSuspend
import com.rasalexman.core.common.extensions.mapListTo
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.data.repository.IGenresRepository
import com.rasalexman.models.ui.GenreItemUI
import com.rasalexman.tabhome.domain.genres.IGetRemoteGenresUseCase

internal class GetRemoteGenresUseCase(
    private val repository: IGenresRepository
) : IGetRemoteGenresUseCase {
    override suspend fun execute(): ResultList<GenreItemUI> {
        return repository
            .getRemoteGenresList()
            .applyIfSuccessSuspend(repository::saveGenresList)
            .mapListTo()
    }
}