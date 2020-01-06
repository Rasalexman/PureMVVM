package com.rasalexman.providers.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.providers.database.dao.base.IBaseDao

@Dao
interface IMoviesDao : IBaseDao<MovieEntity> {

    @Query("SELECT * FROM MovieEntity WHERE genreIds LIKE '%' || :genreId  || '%' ORDER BY releaseDate DESC")
    fun getAll(genreId: Int): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT COUNT(*) FROM MovieEntity WHERE genreIds LIKE '%' || :genreId  || '%' ORDER BY releaseDate DESC")
    suspend fun getCount(genreId: Int): Int

    @Query("SELECT * FROM MovieEntity WHERE id = :movieId LIMIT 1")
    suspend fun getById(movieId: Int): MovieEntity?
}