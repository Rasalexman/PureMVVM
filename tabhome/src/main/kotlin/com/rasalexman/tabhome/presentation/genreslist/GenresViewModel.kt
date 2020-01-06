package com.rasalexman.tabhome.presentation.genreslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.loadingResult
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.coroutinesmanager.CoroutinesProvider
import com.rasalexman.tabhome.domain.genres.GetGenresUseCase
import com.rasalexman.core.presentation.viewModels.BaseViewModel

class GenresViewModel : BaseViewModel() {

    private val getGenresUseCase: GetGenresUseCase by immutableInstance()

    override val resultLiveData: LiveData<SResult<List<GenreItemUI>>> by unsafeLazy {
        liveData(viewModelScope.coroutineContext + CoroutinesProvider.IO) {
            emit(loadingResult())
            emit(getGenresUseCase.execute())
        }
    }
}