package com.rasalexman.tabhome.domain.genres.impl

import com.rasalexman.core.common.extensions.mapIfSuccessSuspend
import com.rasalexman.core.common.extensions.toSuccessResult
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.models.ui.GenreItemUI
import com.rasalexman.tabhome.domain.genres.IGetGenresUseCase
import com.rasalexman.tabhome.domain.genres.IGetLocalGenresUseCase
import com.rasalexman.tabhome.domain.genres.IGetRemoteGenresUseCase

internal class GetGenresUseCase(
    private val getLocalGenresUseCase: IGetLocalGenresUseCase,
    private val getRemoteGenresUseCase: IGetRemoteGenresUseCase
) : IGetGenresUseCase {
    override suspend fun execute(): ResultList<GenreItemUI> {
        return getLocalGenresUseCase.execute().mapIfSuccessSuspend {
            this.takeIf { it.isNotEmpty() }?.toSuccessResult() ?: getRemoteGenresUseCase.execute()
        }
    }
}