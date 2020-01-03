package com.rasalexman.core.data.base

interface IBaseLocalRepository<out L : ILocalDataSource> {
    val localDataSource: L
}
