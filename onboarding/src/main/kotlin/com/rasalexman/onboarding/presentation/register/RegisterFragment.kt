package com.rasalexman.onboarding.presentation.register

import android.view.View
import androidx.core.util.PatternsCompat
import androidx.fragment.app.viewModels
import com.rasalexman.core.common.extensions.*
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.presentation.BaseFragment
import com.rasalexman.onboarding.R
import com.rasalexman.onboarding.data.SignUpEventModel
import com.rasalexman.providers.data.models.toUserEmail
import com.rasalexman.providers.data.models.toUserName
import com.rasalexman.providers.data.models.toUserPassword
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.contentLayout
import kotlinx.android.synthetic.main.fragment_register.emailEditText
import kotlinx.android.synthetic.main.fragment_register.loadingLayout
import kotlinx.android.synthetic.main.fragment_register.passwordEditText

class RegisterFragment : BaseFragment<RegisterViewModel>() {

    override val viewModel: RegisterViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_register

    override val loadingViewLayout: View?
        get() = loadingLayout

    override val contentViewLayout: View?
        get() = contentLayout

    override fun initLayout() {
        backButton.setOnClickListener {
            onBackPressed()
        }
        signUpButton.setOnClickListener(::onSignUpClickHandler)
    }

    override fun onResultHandler(result: SResult<*>) {
        super.onResultHandler(result.mapIfType<SResult.Success<Boolean>> {
            mainNavigator().showTabsDirection.toNavResult(mainNavigator().hostController)
        })
    }

    override fun onBackPressed(): Boolean {
        onboardingNavigator().hostController.popBackStack()
        return false
    }

    private fun onSignUpClickHandler(view: View) {
        nameEditText.error = null
        emailEditText.error = null
        passwordEditText.error = null
        repeatEditText.error = null

        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val repeatPassword = repeatEditText.text.toString()

        if(name.isEmpty()) {
            nameEditText.error = string(R.string.error_user_name_empty)
        } else if(!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = string(R.string.error_email_match)
        } else if(password.isEmpty()) {
            passwordEditText.error = string(R.string.error_password_empty)
        } else if(repeatPassword.isEmpty()) {
            repeatEditText.error = string(R.string.error_password_repeat_empty)
        } else if(password != repeatPassword) {
            repeatEditText.error = string(R.string.error_password_repeat_match)
        } else {
            fetchWith(SignUpEventModel(
                name.toUserName(),
                email.toUserEmail(),
                password.toUserPassword()
            ))
        }
    }
}