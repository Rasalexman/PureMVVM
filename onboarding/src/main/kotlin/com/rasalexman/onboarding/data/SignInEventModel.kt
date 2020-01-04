package com.rasalexman.onboarding.data

import com.rasalexman.providers.data.models.UserEmail
import com.rasalexman.providers.data.models.UserPassword

data class SignInEventModel(
    val email: UserEmail,
    val password: UserPassword
)