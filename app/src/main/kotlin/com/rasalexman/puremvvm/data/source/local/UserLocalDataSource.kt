package com.rasalexman.puremvvm.data.source.local

import com.rasalexman.providers.data.models.UserEmail
import com.rasalexman.providers.data.models.UserName
import com.rasalexman.providers.data.models.UserPassword
import com.rasalexman.providers.data.source.local.IUserLocalDataSource
import com.rasalexman.providers.database.dao.IUserDao
import com.rasalexman.providers.data.models.local.UserEntity
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
        userPreference.create(userName, userEmail, userPassword)
        userDao.insert(
            UserEntity(
                name = userName.value,
                email = userEmail.value,
                password = userPassword.value
            )
        )
    }
}