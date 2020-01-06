package com.rasalexman.providers.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rasalexman.providers.data.models.local.GenreEntity
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.providers.database.converters.FromListOfIntToStringConverter
import com.rasalexman.providers.database.converters.FromListOfStringsToStringConverter
import com.rasalexman.providers.database.dao.IGenresDao
import com.rasalexman.providers.database.dao.IMoviesDao
import com.rasalexman.providers.database.dao.IUserDao
import com.rasalexman.providers.database.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        GenreEntity::class,
        MovieEntity::class
    ], version = 1
)
@TypeConverters(FromListOfIntToStringConverter::class, FromListOfStringsToStringConverter::class)
abstract class AppDatabase : RoomDatabase() {
        abstract fun getUserDao(): IUserDao
        abstract fun getGenresDao(): IGenresDao
        abstract fun getMoviesDao(): IMoviesDao
}
