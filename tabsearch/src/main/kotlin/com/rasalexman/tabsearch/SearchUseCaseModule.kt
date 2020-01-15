package com.rasalexman.tabsearch

import com.mincor.kodi.core.*
import com.rasalexman.tabsearch.domain.GetSearchDataSource
import com.rasalexman.tabsearch.domain.IGetSearchDataSource

val tabSearchModule = kodiModule {
    bind<IGetSearchDataSource>() with provider { GetSearchDataSource(instance()) }
}