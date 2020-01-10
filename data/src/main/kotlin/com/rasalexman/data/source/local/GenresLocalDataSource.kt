package com.rasalexman.data.source.local

import com.rasalexman.core.common.extensions.toSuccessResult
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.models.local.GenreEntity
import com.rasalexman.providers.database.dao.IGenresDao

class GenresLocalDataSource(
    private val genresDao: IGenresDao
) : IGenresLocalDataSource {

    override suspend fun getGenresList(): SResult<List<GenreEntity>> {
        return genresDao.getAll().toSuccessResult()
    }

    override suspend fun saveGenresList(data: List<GenreEntity>) {
        genresDao.insertAll(data)
    }
}