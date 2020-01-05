package com.rasalexman.tabhome.presentation

import com.rasalexman.core.presentation.BaseHostFragment
import com.rasalexman.core.presentation.viewModels.IBaseViewModel
import com.rasalexman.tabhome.R

class HomeTabHostFragment : BaseHostFragment<IBaseViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_host_tab_home

    override val navControllerId: Int
        get() = R.id.homeTabHostFragment

    override val navigatorTag: String
        get() = ""

    override fun bindNavController() = Unit
    override fun unbindNavController() = Unit
}