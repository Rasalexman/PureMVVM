package com.rasalexman.onboarding.presentation.login

import androidx.lifecycle.*
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.applyForType
import com.rasalexman.core.common.extensions.loadingResult
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.coroutinesmanager.CoroutinesProvider
import com.rasalexman.onboarding.data.SignInEventModel
import com.rasalexman.onboarding.domain.CheckUserRegisteredUseCase
import com.rasalexman.core.presentation.viewModels.BaseViewModel

class LoginViewModel : BaseViewModel() {

    private val checkUserRegisteredUseCase: CheckUserRegisteredUseCase by immutableInstance()
    private val loginLiveData by unsafeLazy { MutableLiveData<SEvent.FetchWith<SignInEventModel>>() }

    override val resultLiveData: LiveData<SResult<Boolean>> by unsafeLazy {
        loginLiveData.switchMap {
            liveData(viewModelScope.coroutineContext + CoroutinesProvider.IO) {
                emit(loadingResult())
                emit(checkUserRegisteredUseCase.execute(it.data))
            }
        }
    }

    override fun processViewEvent(viewEvent: SEvent) {
        viewEvent.applyForType(loginLiveData::setValue)
    }
}