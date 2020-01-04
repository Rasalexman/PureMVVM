package com.rasalexman.providers.data.source.local

import com.rasalexman.core.data.base.ILocalDataSource
import com.rasalexman.providers.data.models.UserEmail
import com.rasalexman.providers.data.models.UserName
import com.rasalexman.providers.data.models.UserPassword
import com.rasalexman.providers.database.entities.UserEntity

interface IUserLocalDataSource : ILocalDataSource {
    suspend fun getUserByEmail(userEmail: UserEmail): UserEntity?
    suspend fun saveUserData(
        userName: UserName,
        userEmail: UserEmail,
        userPassword: UserPassword
    )
}