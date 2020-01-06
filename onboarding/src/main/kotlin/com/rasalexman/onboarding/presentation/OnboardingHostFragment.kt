package com.rasalexman.onboarding.presentation

import com.mincor.kodi.core.bindTag
import com.mincor.kodi.core.single
import com.mincor.kodi.core.with
import com.rasalexman.core.common.navigation.Navigator
import com.rasalexman.core.presentation.BaseHostFragment
import com.rasalexman.core.presentation.viewModels.IBaseViewModel
import com.rasalexman.onboarding.R

class OnboardingHostFragment : BaseHostFragment<IBaseViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_host_onboarding

    override val navControllerId: Int
        get() = R.id.onboardingHostFragment

    override val navigatorTag: String
        get() = Navigator.ONBOARDING_NAVIGATOR

    override fun bindNavController() {
        bindTag(navigatorTag) with single {
            Navigator.OnboardingNavigator(hostController = navigatorController)
        }
    }
}