package com.rasalexman.tabhome.presentation.genreslist

import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.asyncLiveData
import com.rasalexman.core.common.extensions.loadingResult
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.models.ui.GenreItemUI
import com.rasalexman.tabhome.domain.genres.IGetGenresUseCase

class GenresViewModel : BaseViewModel() {

    private val getGenresUseCase: IGetGenresUseCase by immutableInstance()

    override val resultLiveData by unsafeLazy {
        asyncLiveData<ResultList<GenreItemUI>> {
            emit(loadingResult())
            emit(getGenresUseCase.execute())
        }
    }
}