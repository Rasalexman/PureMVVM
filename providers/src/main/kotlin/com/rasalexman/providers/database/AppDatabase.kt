package com.rasalexman.providers.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rasalexman.providers.database.converters.FromListOfIntToStringConverter
import com.rasalexman.providers.database.converters.FromListOfStringsToStringConverter
import com.rasalexman.providers.database.dao.IUserDao
import com.rasalexman.providers.database.entities.UserEntity

@Database(
    entities = [
        UserEntity::class
    ], version = 1
)
@TypeConverters(FromListOfIntToStringConverter::class, FromListOfStringsToStringConverter::class)
abstract class AppDatabase : RoomDatabase() {
        abstract fun getUserDao(): IUserDao
}
