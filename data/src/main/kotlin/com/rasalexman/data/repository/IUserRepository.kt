package com.rasalexman.data.repository

import com.rasalexman.core.data.base.IBaseLocalRepository
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.models.inline.UserEmail
import com.rasalexman.models.inline.UserName
import com.rasalexman.models.inline.UserPassword
import com.rasalexman.models.local.UserEntity
import com.rasalexman.data.source.local.IUserLocalDataSource

interface IUserRepository : IBaseLocalRepository<IUserLocalDataSource> {

    suspend fun getUser(userEmail: UserEmail): SResult<UserEntity>
    suspend fun saveUserData(userName: UserName, userEmail: UserEmail, userPassword: UserPassword)
}