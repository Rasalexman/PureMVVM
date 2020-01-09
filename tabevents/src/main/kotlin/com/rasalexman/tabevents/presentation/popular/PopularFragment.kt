package com.rasalexman.tabevents.presentation.popular

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.mikepenz.fastadapter.paged.ExperimentalPagedSupport
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.presentation.recyclerview.paged.BasePagedRecyclerFragment
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.tabhome.presentation.movieslist.MovieItemUI
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalPagedSupport
class PopularFragment : BasePagedRecyclerFragment<MovieEntity, MovieItemUI, PopularViewModel>() {

    override val viewModel: PopularViewModel by viewModels()

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

    companion object {
        fun newInstance() = PopularFragment()
    }
}