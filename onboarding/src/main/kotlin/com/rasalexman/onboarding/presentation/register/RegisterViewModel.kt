package com.rasalexman.onboarding.presentation.register

import androidx.lifecycle.*
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.applyForType
import com.rasalexman.core.common.extensions.loadingResult
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.coroutinesmanager.CoroutinesProvider
import com.rasalexman.onboarding.data.SignUpEventModel
import com.rasalexman.onboarding.domain.RegisterUserUseCase
import io.quasa.quasaconnect.core.presentation.viewModels.BaseViewModel

class RegisterViewModel : BaseViewModel() {

    private val registerUserUseCase: RegisterUserUseCase by immutableInstance()
    private val loginLiveData by unsafeLazy { MutableLiveData<SEvent.FetchWith<SignUpEventModel>>() }

    override val observableLiveData: LiveData<SResult<Boolean>> by unsafeLazy {
        loginLiveData.switchMap {
            liveData(viewModelScope.coroutineContext + CoroutinesProvider.IO) {
                emit(loadingResult())
                emit(registerUserUseCase.execute(it.data))
            }
        }
    }

    override fun processViewEvent(viewEvent: SEvent) {
        viewEvent.applyForType(loginLiveData::setValue)
    }
}