package com.rasalexman.providers.preference

import android.content.Context
import com.chibatching.kotpref.KotprefModel
import com.rasalexman.providers.data.models.UserEmail
import com.rasalexman.providers.data.models.UserName
import com.rasalexman.providers.data.models.UserPassword
import java.util.*

internal class UserPreference(context: Context) : KotprefModel(context), IUserPreference {
    override var name: String           by stringPref("")
    override var email: String          by stringPref("")
    override var password: String       by stringPref("")
    override var createdAt: String      by stringPref("")
    override var updatedAt: String      by stringPref("")

    override fun create(userName: UserName, userEmail: UserEmail, userPassword: UserPassword) {
        name = userName.value
        email = userEmail.value
        password = userPassword.value
        val date = Calendar.getInstance(Locale.getDefault()).time.toString()
        createdAt = date
        updatedAt = date
    }
}