package com.rasalexman.providers.preference

import androidx.lifecycle.LiveData
import com.rasalexman.models.inline.UserEmail
import com.rasalexman.models.inline.UserName
import com.rasalexman.models.inline.UserPassword
import kotlin.reflect.KProperty0

interface IUserPreference {
    var name: String
    var email: String
    var photo: String
    var password: String
    var createdAt: String
    var updatedAt: String

    fun create(userName: UserName, userEmail: UserEmail, userPassword: UserPassword)

    fun <K : Any> toLiveData(property: KProperty0<K>): LiveData<K>
}