package com.rasalexman.puremvvm.data.source.remote

import com.rasalexman.core.data.dto.SResult
import com.rasalexman.coroutinesmanager.IAsyncTasksManager
import com.rasalexman.providers.BuildConfig
import com.rasalexman.providers.data.models.local.GenreEntity
import com.rasalexman.providers.data.models.remote.GenreModel
import com.rasalexman.providers.data.source.remote.IGenresRemoteDataSource
import com.rasalexman.providers.network.api.IMovieApi
import com.rasalexman.providers.network.responses.getResult
import kotlinx.coroutines.sync.Mutex

class GenresRemoteDataSource(
    private val moviesApi: IMovieApi
) : IGenresRemoteDataSource, IAsyncTasksManager {

    private val addedImages by lazy { mutableSetOf<String>() }

    override suspend fun getRemoteGenresList(): SResult<List<GenreModel>> {
        return moviesApi.getGenresList().getResult { it.genres }
    }

    override suspend fun getGenresImages(result: List<GenreEntity>) {
        result.forEach { genreEntity ->
            moviesApi.getMoviesListByPopularity(genreEntity.id).run {
                body()?.results?.apply {
                    filter {
                        val path = it.getImagePath()
                        path.isNotEmpty() && !addedImages.contains(path)
                    }.take(3).mapTo(genreEntity.images) {
                        val imagePoster = it.getImagePath()
                        addedImages.add(imagePoster)
                        buildString {
                            append(BuildConfig.IMAGES_URL)
                            append(imagePoster)
                        }
                    }
                }
            }
        }
    }
}