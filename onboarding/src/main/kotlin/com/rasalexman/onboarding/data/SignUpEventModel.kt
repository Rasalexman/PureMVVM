package com.rasalexman.onboarding.data

import com.rasalexman.providers.data.models.UserEmail
import com.rasalexman.providers.data.models.UserName
import com.rasalexman.providers.data.models.UserPassword

data class SignUpEventModel(
    val name: UserName,
    val email: UserEmail,
    val password: UserPassword
)