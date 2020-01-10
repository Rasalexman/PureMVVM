package com.rasalexman.data.source.local

import com.rasalexman.core.data.base.ILocalDataSource
import com.rasalexman.models.inline.UserEmail
import com.rasalexman.models.inline.UserName
import com.rasalexman.models.inline.UserPassword
import com.rasalexman.models.local.UserEntity

interface IUserLocalDataSource : ILocalDataSource {
    suspend fun getUserByEmail(userEmail: UserEmail): UserEntity?
    suspend fun saveUserData(
        userName: UserName,
        userEmail: UserEmail,
        userPassword: UserPassword
    )
}