package com.rasalexman.onboarding.presentation.login

import androidx.core.util.PatternsCompat
import androidx.lifecycle.MutableLiveData
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.*
import com.rasalexman.core.data.dto.FetchWith
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.models.inline.toUserEmail
import com.rasalexman.models.inline.toUserPassword
import com.rasalexman.onboarding.R
import com.rasalexman.onboarding.data.SignInEventModel
import com.rasalexman.onboarding.domain.ICheckUserRegisteredUseCase

class LoginViewModel : BaseViewModel() {

    private val checkUserRegisteredUseCase: ICheckUserRegisteredUseCase by immutableInstance()

    val emailValidationError by unsafeLazy { MutableLiveData<Int>() }
    val passwordValidationError by unsafeLazy { MutableLiveData<Int>() }

    val email by unsafeLazy {
        MutableLiveData<String>()
    }

    val password by unsafeLazy {
        MutableLiveData<String>()
    }

    override val resultLiveData by unsafeLazy {
        onEventResult<FetchWith<SignInEventModel>> {
            emit(loadingResult())
            emit(checkUserRegisteredUseCase.execute(it.data))
        }
    }

    fun logInHandler() {
        val emailToValidate = email.value.orEmpty()
        val passwordToValidate = password.value.orEmpty()

        if(!PatternsCompat.EMAIL_ADDRESS.matcher(emailToValidate).matches()) {
            emailValidationError.value = R.string.error_email_match
        } else if(passwordToValidate.isEmpty()) {
            passwordValidationError.value = R.string.error_password_empty
        } else {
            processViewEvent(
                SignInEventModel(
                    email = emailToValidate.toUserEmail(),
                    password = passwordToValidate.toUserPassword()
                ).toFetch()
            )
        }
    }
}