package com.rasalexman.providers

import androidx.room.Room
import coil.util.CoilUtils
import com.mincor.kodi.core.*
import com.rasalexman.providers.database.AppDatabase
import com.rasalexman.providers.database.dao.IUserDao
import com.rasalexman.providers.network.createOkHttpClient
import com.rasalexman.providers.preference.IUserPreference
import com.rasalexman.providers.preference.UserPreference
import okhttp3.Cache
import okhttp3.OkHttpClient

private val databaseModule = kodiModule {
    bind<AppDatabase>() with single {
        Room.databaseBuilder(
            instance(),
            AppDatabase::class.java, "AppDB.db"
        ).build()
    }

    bind<IUserDao>() with provider { instance<AppDatabase>().getUserDao() }
}

private val networkModule = kodiModule {
    bind<Cache>() with provider { CoilUtils.createDefaultCache(instance()) }
    bind<OkHttpClient>() with single { createOkHttpClient(instance()) }
}

private val preferenceModule = kodiModule {
    bind<IUserPreference>() with single { UserPreference(instance()) }
}

val providersModule = kodiModule {
    import(databaseModule)
    import(networkModule)
    import(preferenceModule)
}
