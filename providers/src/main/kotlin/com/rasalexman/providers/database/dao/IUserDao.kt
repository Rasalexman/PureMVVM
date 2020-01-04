package com.rasalexman.providers.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.rasalexman.providers.data.models.UserEmail
import com.rasalexman.providers.database.dao.base.IBaseDao
import com.rasalexman.providers.database.entities.UserEntity

@Dao
interface IUserDao : IBaseDao<UserEntity> {

    @Query("SELECT * FROM UserEntity WHERE email = :email LIMIT 1")
    suspend fun selectByEmail(email: String): UserEntity?
}