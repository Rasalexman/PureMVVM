package com.rasalexman.providers.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.rasalexman.providers.data.models.local.GenreEntity
import com.rasalexman.providers.database.dao.base.IBaseDao

@Dao
interface IGenresDao : IBaseDao<GenreEntity> {

    @Query("SELECT * FROM GenreEntity ORDER BY name ASC")
    suspend fun getAll(): List<GenreEntity>
}