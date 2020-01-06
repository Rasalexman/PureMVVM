package com.rasalexman.tabhome.domain.genres

import com.rasalexman.core.common.extensions.mapIfSuccessSuspend
import com.rasalexman.core.common.extensions.toSuccessResult
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.domain.IUseCase
import com.rasalexman.tabhome.presentation.genreslist.GenreItemUI

class GetGenresUseCase(
    private val getLocalGenresUseCase: GetLocalGenresUseCase,
    private val getRemoteGenresUseCase: GetRemoteGenresUseCase
) : IUseCase.Out<SResult<List<GenreItemUI>>> {
    override suspend fun execute(): SResult<List<GenreItemUI>> {
        return getLocalGenresUseCase.execute().mapIfSuccessSuspend {
            this.takeIf { it.isNotEmpty() }?.toSuccessResult() ?: getRemoteGenresUseCase.execute()
        }
    }
}