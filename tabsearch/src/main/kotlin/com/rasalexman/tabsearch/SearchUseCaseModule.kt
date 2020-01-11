package com.rasalexman.tabsearch

import com.mincor.kodi.core.*
import com.rasalexman.tabsearch.domain.GetSearchDataSource

val tabSearchModule = kodiModule {
    bind<GetSearchDataSource>() with provider { GetSearchDataSource(instance()) }
}