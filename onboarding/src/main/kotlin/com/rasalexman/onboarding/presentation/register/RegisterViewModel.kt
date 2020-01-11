package com.rasalexman.onboarding.presentation.register

import androidx.core.util.PatternsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.mincor.kodi.core.immutableInstance
import com.rasalexman.core.common.extensions.applyForType
import com.rasalexman.core.common.extensions.loadingResult
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.coroutinesmanager.CoroutinesProvider
import com.rasalexman.models.inline.toUserEmail
import com.rasalexman.models.inline.toUserName
import com.rasalexman.models.inline.toUserPassword
import com.rasalexman.onboarding.data.SignUpEventModel
import com.rasalexman.onboarding.domain.RegisterUserUseCase

class RegisterViewModel : BaseViewModel() {

    private val registerUserUseCase: RegisterUserUseCase by immutableInstance()
    private val loginLiveData by unsafeLazy { MutableLiveData<SignUpEventModel>() }

    val nameValidationError by unsafeLazy { MutableLiveData<Int>() }
    val emailValidationError by unsafeLazy { MutableLiveData<Int>() }
    val passwordValidationError by unsafeLazy { MutableLiveData<Int>() }
    val repeatValidationError by unsafeLazy { MutableLiveData<Int>() }

    val name by unsafeLazy { MutableLiveData<String>() }
    val email by unsafeLazy { MutableLiveData<String>() }
    val password by unsafeLazy { MutableLiveData<String>() }
    val repeatPassword by unsafeLazy { MutableLiveData<String>() }

    override val resultLiveData by unsafeLazy {
        loginLiveData.switchMap {
            liveData(viewModelScope.coroutineContext + CoroutinesProvider.IO) {
                emit(loadingResult())
                emit(registerUserUseCase.execute(it))
            }
        }
    }

    fun onRegisterHandler() {
        val nameStr = name.value.orEmpty()
        val emailStr = email.value.orEmpty()
        val passwordStr = password.value.orEmpty()
        val repeatPasswordStr = repeatPassword.value.orEmpty()

        if(nameStr.isEmpty()) {
            nameValidationError.value = com.rasalexman.onboarding.R.string.error_user_name_empty
        } else if(!PatternsCompat.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            emailValidationError.value = com.rasalexman.onboarding.R.string.error_email_match
        } else if(passwordStr.isEmpty()) {
            passwordValidationError.value = com.rasalexman.onboarding.R.string.error_password_empty
        } else if(repeatPasswordStr.isEmpty()) {
            repeatValidationError.value = com.rasalexman.onboarding.R.string.error_password_repeat_empty
        } else if(passwordStr != repeatPasswordStr) {
            repeatValidationError.value = com.rasalexman.onboarding.R.string.error_password_repeat_match
        } else {
            loginLiveData.value = SignUpEventModel(
                name = nameStr.toUserName(),
                email = emailStr.toUserEmail(),
                password = passwordStr.toUserPassword()
            )
        }
    }
}