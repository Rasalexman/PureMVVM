package com.rasalexman.core.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mincor.kodi.core.unbind
import com.rasalexman.core.common.navigation.Navigator
import com.rasalexman.core.presentation.viewModels.IBaseViewModel

abstract class BaseHostFragment<out VM : IBaseViewModel> : BaseFragment<VM>(), IBaseHost {

    override val navigatorController: NavController
        get() = Navigation.findNavController(
            requireActivity(),
            navControllerId
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unbind<Navigator>(navigatorTag)
        bindNavController()
    }
}