package com.rasalexman.puremvvm.data.repository

import com.rasalexman.core.common.extensions.alertResult
import com.rasalexman.core.common.extensions.doIfNull
import com.rasalexman.core.common.extensions.toSuccessResult
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.data.errors.QException
import com.rasalexman.providers.data.models.UserEmail
import com.rasalexman.providers.data.models.UserName
import com.rasalexman.providers.data.models.UserPassword
import com.rasalexman.providers.data.repository.IUserRepository
import com.rasalexman.providers.data.source.local.IUserLocalDataSource
import com.rasalexman.providers.database.entities.UserEntity

class UserRepository(
    override val localDataSource: IUserLocalDataSource
) : IUserRepository {

    override suspend fun getUser(userEmail: UserEmail): SResult<UserEntity> {
        return localDataSource.getUserByEmail(userEmail)?.toSuccessResult()
            .doIfNull { alertResult(exception = QException.AuthErrors.UserNullError()) }
    }

    override suspend fun saveUserData(
        userName: UserName,
        userEmail: UserEmail,
        userPassword: UserPassword
    ) {
        localDataSource.saveUserData(userName, userEmail, userPassword)
    }
}