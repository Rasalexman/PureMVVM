package com.rasalexman.core.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mincor.kodi.core.bind
import com.mincor.kodi.core.provider
import com.mincor.kodi.core.unbind
import com.mincor.kodi.core.with
import com.rasalexman.core.common.navigation.QNavigator
import com.rasalexman.core.presentation.viewModels.IBaseViewModel

abstract class BaseHostFragment<out VM : IBaseViewModel> : BaseFragment<VM>() {

    open val navControllerId: Int = -1
    open val qNavigator: QNavigator? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavController()
    }

    protected open fun initNavController() {
        navControllerId.takeIf { it > 0 }?.let { navId ->
            qNavigator?.tag?.let { navigatorTag ->
                unbind<NavController>(navigatorTag)
                bind<NavController>(navigatorTag) with provider { Navigation.findNavController(requireActivity(), navId) }
            }
        }
    }
}
