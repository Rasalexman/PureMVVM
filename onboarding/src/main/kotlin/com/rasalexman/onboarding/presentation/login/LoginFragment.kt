package com.rasalexman.onboarding.presentation.login

import android.view.View
import androidx.core.util.PatternsCompat
import androidx.navigation.navGraphViewModels
import com.rasalexman.core.common.extensions.*
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.presentation.BaseFragment
import com.rasalexman.onboarding.R
import com.rasalexman.onboarding.data.SignInEventModel
import com.rasalexman.providers.data.models.toUserEmail
import com.rasalexman.providers.data.models.toUserPassword
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_login

    override val loadingViewLayout: View?
        get() = loadingLayout

    override val contentViewLayout: View?
        get() = contentLayout

    override val viewModel: LoginViewModel by navGraphViewModels(R.id.navigation_onboarding)

    override fun initLayout() {
        signInButton.setOnClickListener(::onSignInClickHandler)
        registerButton.setOnClickListener {
            onboardingNavigator().hostController.navigate(LoginFragmentDirections.showRegisterFragment())
        }
    }

    override fun onResultHandler(result: SResult<*>) {
        super.onResultHandler(result.mapIfType<SResult.Success<Boolean>> {
            mainNavigator().showTabsDirection.toNavResult(mainNavigator().hostController)
        })
    }

    private fun onSignInClickHandler(view: View) {
        emailEditText.error = null
        passwordEditText.error = null

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if(!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = string(R.string.error_email_match)
        } else if(password.isEmpty()) {
            passwordEditText.error = string(R.string.error_password_empty)
        } else {
            fetchWith(SignInEventModel(email.toUserEmail(), password.toUserPassword()))
        }
    }
}