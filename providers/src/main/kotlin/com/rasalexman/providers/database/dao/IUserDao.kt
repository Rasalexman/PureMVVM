package com.rasalexman.providers.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.rasalexman.models.local.UserEntity
import com.rasalexman.providers.database.dao.base.IBaseDao

@Dao
interface IUserDao : IBaseDao<UserEntity> {

    @Query("SELECT * FROM UserEntity WHERE email = :email LIMIT 1")
    suspend fun selectByEmail(email: String): UserEntity?
}