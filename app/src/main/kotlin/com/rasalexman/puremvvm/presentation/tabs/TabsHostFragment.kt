package com.rasalexman.puremvvm.presentation.tabs

import androidx.navigation.ui.NavigationUI
import com.mincor.kodi.core.bind
import com.mincor.kodi.core.single
import com.mincor.kodi.core.with
import com.rasalexman.core.common.navigation.Navigator
import com.rasalexman.core.presentation.BaseHostFragment
import com.rasalexman.core.presentation.viewModels.IBaseViewModel
import com.rasalexman.puremvvm.R
import kotlinx.android.synthetic.main.fragment_host_tabs.*

class TabsHostFragment : BaseHostFragment<IBaseViewModel>() {

    override val navControllerId: Int
        get() = R.id.mainTabsHostFragment

    override val layoutId: Int
        get() = R.layout.fragment_host_tabs

    override val navigatorTag: String
        get() = Navigator.TAB_NAVIGATOR

    override fun bindNavController() {
        navController.let { currentNavController ->
            NavigationUI.setupWithNavController(bottomNavigationView, currentNavController)
            bind<Navigator>(navigatorTag) with single { Navigator.TabNavigator(hostController = currentNavController) }
        }
    }
}