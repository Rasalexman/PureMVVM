package com.rasalexman.providers.data.repository

import com.rasalexman.core.data.base.IBaseLocalRepository
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.providers.data.source.local.IUserLocalDataSource
import com.rasalexman.providers.data.models.UserEmail
import com.rasalexman.providers.data.models.UserName
import com.rasalexman.providers.data.models.UserPassword
import com.rasalexman.providers.data.models.local.UserEntity

interface IUserRepository : IBaseLocalRepository<IUserLocalDataSource> {

    suspend fun getUser(userEmail: UserEmail): SResult<UserEntity>
    suspend fun saveUserData(userName: UserName, userEmail: UserEmail, userPassword: UserPassword)
}