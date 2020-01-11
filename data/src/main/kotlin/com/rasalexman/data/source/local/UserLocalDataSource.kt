package com.rasalexman.data.source.local

import com.rasalexman.models.inline.UserEmail
import com.rasalexman.models.inline.UserName
import com.rasalexman.models.inline.UserPassword
import com.rasalexman.models.local.UserEntity
import com.rasalexman.providers.database.dao.IUserDao
import com.rasalexman.providers.preference.IUserPreference

class UserLocalDataSource(
    private val userDao: IUserDao,
    private val userPreference: IUserPreference
) : IUserLocalDataSource {

    override suspend fun getUserByEmail(userEmail: UserEmail): UserEntity? {
        return userDao.selectByEmail(userEmail.value)
    }

    override suspend fun saveUserData(
        userName: UserName,
        userEmail: UserEmail,
        userPassword: UserPassword
    ) {
        userPreference.createUser(userName, userEmail, userPassword)
        userDao.insert(
            UserEntity(
                name = userName.value,
                email = userEmail.value,
                password = userPassword.value
            )
        )
    }
}