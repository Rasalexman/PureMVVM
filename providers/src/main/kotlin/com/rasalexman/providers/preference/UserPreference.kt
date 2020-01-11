package com.rasalexman.providers.preference

import android.content.Context
import androidx.lifecycle.LiveData
import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.livedata.asLiveData
import com.rasalexman.models.inline.UserEmail
import com.rasalexman.models.inline.UserName
import com.rasalexman.models.inline.UserPassword
import java.util.*
import kotlin.reflect.KProperty0

internal class UserPreference(context: Context) : KotprefModel(context), IUserPreference {
    override var name: String           by stringPref("")
    override var email: String          by stringPref("")
    override var photo: String          by stringPref("")
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

    override fun <K : Any> toLiveData(property:KProperty0<K>): LiveData<K> {
        return this.asLiveData(property)
    }
}