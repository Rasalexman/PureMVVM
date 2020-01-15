package com.rasalexman.puremvvm.presentation.start

import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.asyncLiveData
import com.rasalexman.core.common.extensions.toNavResult
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.providers.preference.IUserPreference
import com.rasalexman.puremvvm.NavigationMainDirections

class StartViewModel : BaseViewModel() {

    private val userPreference: IUserPreference by immutableInstance()

    override val resultLiveData by unsafeLazy {
        asyncLiveData<SResult<Any>> {
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
