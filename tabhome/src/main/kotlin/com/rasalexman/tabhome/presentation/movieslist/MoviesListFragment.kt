package com.rasalexman.tabhome.presentation.movieslist

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.navigation.fragment.navArgs
import androidx.paging.PagedList
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.mikepenz.fastadapter.paged.ExperimentalPagedSupport
import com.rasalexman.core.common.extensions.log
import com.rasalexman.core.common.extensions.toFetch
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.presentation.recyclerview.paged.BaseToolbarPagedRecyclerFragment
import com.rasalexman.providers.data.models.local.MovieEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalPagedSupport
class MoviesListFragment : BaseToolbarPagedRecyclerFragment<MovieEntity, MovieItemUI, MoviesViewModel>() {

    private val moviesNavArgs: MoviesListFragmentArgs by navArgs()

    override val viewModel: MoviesViewModel by viewModels()

    override val toolbarTitle: String
        get() = moviesNavArgs.itemName

    override val needBackButton: Boolean
        get() = true

    override val asyncDifferConfig: AsyncDifferConfig<MovieEntity> by unsafeLazy {
        AsyncDifferConfig.Builder<MovieEntity>(object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
                oldItem == newItem
        }).build()
    }

    override val placeholderInterceptor: (Int) -> MovieItemUI = {
        MovieItemUI.createPlaceHolderItem()
    }

    override val interceptor: (MovieEntity) -> MovieItemUI = {
        it.run {
            MovieItemUI(
                id = id,
                voteCount = voteCount,
                voteAverage = voteAverage,
                isVideo = isVideo,
                title = title,
                popularity = popularity,
                posterPath = posterPath.takeIf { it.isNotEmpty() } ?: backdropPath,
                originalLanguage = originalLanguage,
                originalTitle = originalTitle,
                genreIds = genreIds,
                backdropPath = backdropPath,
                releaseDate = releaseDate.takeIf { dt ->
                    dt > 0L
                }?.let {
                    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(releaseDate))
                }.orEmpty(),
                adult = adult,
                overview = overview
            )
        }
    }

    init {
        lifecycleScope.launch {
            whenCreated {
                processViewEvent(moviesNavArgs.itemId.toFetch())
            }
        }
    }
}