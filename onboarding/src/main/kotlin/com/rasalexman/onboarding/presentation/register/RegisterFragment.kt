package com.rasalexman.onboarding.presentation.register

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.rasalexman.core.common.extensions.doIfSuccess
import com.rasalexman.core.common.extensions.hideKeyboard
import com.rasalexman.core.common.extensions.mainNavigator
import com.rasalexman.core.common.extensions.onboardingNavigator
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.presentation.BaseBindingFragment
import com.rasalexman.core.presentation.EditorActionListener
import com.rasalexman.onboarding.R
import com.rasalexman.onboarding.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseBindingFragment<FragmentRegisterBinding, RegisterViewModel>(),
    EditorActionListener {

    override val viewModel: RegisterViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_register

    override val loadingViewLayout: View?
        get() = loadingLayout

    override val contentViewLayout: View?
        get() = contentLayout

    override val navController: NavController
        get() = onboardingNavigator().hostController

    override val canGoBack: Boolean
        get() = true

    override fun initBinding(binding: FragmentRegisterBinding) {
        binding.viewModel = viewModel
        binding.fragment = this
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        return when(actionId) {
            EditorInfo.IME_ACTION_GO,
            EditorInfo.IME_ACTION_DONE -> {
                hideKeyboard()
                true
            }
            else -> false
        }
    }

    override fun onResultHandler(result: SResult<*>) {
        super.onResultHandler(result.doIfSuccess(mainNavigator().showTabsHandler))
    }
}