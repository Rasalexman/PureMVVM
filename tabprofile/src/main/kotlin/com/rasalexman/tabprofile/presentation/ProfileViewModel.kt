package com.rasalexman.tabprofile.presentation

import androidx.lifecycle.LiveData
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.mainNavigator
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.providers.preference.IUserPreference

class ProfileViewModel : BaseViewModel() {

    private val userProfile: IUserPreference by immutableInstance()

    val userName: LiveData<String> by unsafeLazy { userProfile.toLiveData(userProfile::name) }
    val userEmail: LiveData<String> by unsafeLazy { userProfile.toLiveData(userProfile::email) }
    val userPhoto: LiveData<String> by unsafeLazy { userProfile.toLiveData(userProfile::photo) }


    fun logOutHandler() {
        userProfile.clearUser()
        mainNavigator().showOnboardingHandler()
    }
}