package com.rasalexman.providers.preference

import com.rasalexman.models.inline.UserEmail
import com.rasalexman.models.inline.UserName
import com.rasalexman.models.inline.UserPassword

interface IUserPreference {
    var name: String
    var email: String
    var password: String
    var createdAt: String
    var updatedAt: String

    fun create(userName: UserName, userEmail: UserEmail, userPassword: UserPassword)
}