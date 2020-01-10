package com.rasalexman.tabhome.presentation.movieslist

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.mikepenz.fastadapter.paged.ExperimentalPagedSupport
import com.rasalexman.core.common.extensions.toFetch
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.presentation.recyclerview.paged.BaseToolbarPagedRecyclerFragment
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.tabhome.data.convert
import kotlinx.coroutines.launch

@ExperimentalPagedSupport
class MoviesListFragment : BaseToolbarPagedRecyclerFragment<MovieItemUI, MovieItemUI, MoviesViewModel>() {

    private val moviesNavArgs: MoviesListFragmentArgs by navArgs()

    override val viewModel: MoviesViewModel by viewModels()

    override val toolbarTitle: String
        get() = moviesNavArgs.itemName

    override val needBackButton: Boolean
        get() = true

    override val asyncDifferConfig: AsyncDifferConfig<MovieItemUI> by unsafeLazy {
        AsyncDifferConfig.Builder<MovieItemUI>(object : DiffUtil.ItemCallback<MovieItemUI>() {
            override fun areItemsTheSame(oldItem: MovieItemUI, newItem: MovieItemUI) =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: MovieItemUI, newItem: MovieItemUI) =
                oldItem == newItem
        }).build()
    }

    override val placeholderInterceptor: (Int) -> MovieItemUI = {
        MovieItemUI.createPlaceHolderItem()
    }

    override val interceptor: (MovieItemUI) -> MovieItemUI = {
        it
    }

    init {
        lifecycleScope.launch {
            whenCreated {
                processViewEvent(moviesNavArgs.itemId.toFetch())
            }
        }
    }
}