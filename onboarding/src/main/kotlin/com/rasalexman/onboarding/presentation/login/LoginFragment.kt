package com.rasalexman.onboarding.presentation.login

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.navGraphViewModels
import com.rasalexman.core.common.extensions.doIfSuccess
import com.rasalexman.core.common.extensions.mainNavigator
import com.rasalexman.core.common.extensions.onboardingNavigator
import com.rasalexman.core.common.extensions.toNavResult
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.presentation.BaseBindingFragment
import com.rasalexman.onboarding.R
import com.rasalexman.onboarding.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseBindingFragment<FragmentLoginBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_login

    override val loadingViewLayout: View?
        get() = loadingLayout

    override val contentViewLayout: View?
        get() = contentLayout

    override val viewModel: LoginViewModel by navGraphViewModels(R.id.navigation_onboarding)

    override val navController: NavController
        get() = onboardingNavigator().hostController

    override fun initBinding(binding: FragmentLoginBinding) {
        binding.viewModel = viewModel
        binding.fragment = this
    }

    override fun onResultHandler(result: SResult<*>) {
        super.onResultHandler(result.doIfSuccess(mainNavigator().showTabsHandler))
    }

    fun showRegisterFragment() {
        super.onResultHandler(
            LoginFragmentDirections.showRegisterFragment().toNavResult()
        )
    }
}