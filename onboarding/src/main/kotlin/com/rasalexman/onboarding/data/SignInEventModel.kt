package com.rasalexman.onboarding.data

import com.rasalexman.models.inline.UserEmail
import com.rasalexman.models.inline.UserPassword

data class SignInEventModel(
    val email: UserEmail,
    val password: UserPassword
)