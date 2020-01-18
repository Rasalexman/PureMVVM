package com.rasalexman.tabhome.presentation

import com.mincor.kodi.core.bindTag
import com.mincor.kodi.core.single
import com.mincor.kodi.core.with
import com.rasalexman.core.common.navigation.Navigator
import com.rasalexman.core.presentation.BaseHostFragment
import com.rasalexman.core.presentation.viewModels.IBaseViewModel
import com.rasalexman.tabhome.R

class HomeTabHostFragment : BaseHostFragment<IBaseViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_host_tab_home

    override val navControllerId: Int
        get() = R.id.homeTabHostFragment

    override val navigatorTag: String
        get() = Navigator.HOME_TAB_NAVIGATOR

    override fun bindNavController() {
        bindTag(navigatorTag) with single {
            Navigator.HomeTabNavigator(hostController = navController)
        }
    }
}