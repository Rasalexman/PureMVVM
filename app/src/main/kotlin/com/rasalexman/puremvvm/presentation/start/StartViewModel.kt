package com.rasalexman.puremvvm.presentation.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.mainNavigator
import com.rasalexman.core.common.extensions.toNavResult
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.coroutinesmanager.CoroutinesProvider
import com.rasalexman.providers.preference.IUserPreference
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.puremvvm.NavigationMainDirections

class StartViewModel : BaseViewModel() {

    private val userPreference: IUserPreference by immutableInstance()

    override val resultLiveData: LiveData<SResult<Any>> by unsafeLazy {
        liveData(viewModelScope.coroutineContext + CoroutinesProvider.IO) {
            emit(getResult())
        }
    }

    private fun getResult(): SResult<Any> {
        return if (userPreference.email.isNotEmpty()) {
            NavigationMainDirections.showTabsFragment()
        } else {
            NavigationMainDirections.showOnboardingFragment()
        }.toNavResult()
    }
}
