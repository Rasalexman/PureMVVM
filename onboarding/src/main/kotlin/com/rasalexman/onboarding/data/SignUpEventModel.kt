package com.rasalexman.onboarding.data

import com.rasalexman.models.inline.UserEmail
import com.rasalexman.models.inline.UserName
import com.rasalexman.models.inline.UserPassword

data class SignUpEventModel(
    val name: UserName,
    val email: UserEmail,
    val password: UserPassword
)