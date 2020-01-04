package com.rasalexman.providers.preference

import com.rasalexman.providers.data.models.UserEmail
import com.rasalexman.providers.data.models.UserName
import com.rasalexman.providers.data.models.UserPassword

interface IUserPreference {
    var name: String
    var email: String
    var password: String
    var createdAt: String
    var updatedAt: String

    fun create(userName: UserName, userEmail: UserEmail, userPassword: UserPassword)
}