package com.rasalexman.tabevents.presentation.popular

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.mikepenz.fastadapter.paged.ExperimentalPagedSupport
import com.rasalexman.core.common.extensions.refresh
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.presentation.recyclerview.paged.BasePagedRefreshRecyclerFragment
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.tabhome.data.convert
import com.rasalexman.tabhome.presentation.movieslist.MovieItemUI

@ExperimentalPagedSupport
class PopularFragment : BasePagedRefreshRecyclerFragment<MovieItemUI, MovieItemUI, PopularViewModel>() {

    override val viewModel: PopularViewModel by viewModels()

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

    override val interceptor: (MovieItemUI) -> MovieItemUI = { it }

    override fun onRefresh() {
        refresh()
    }

    companion object {
        fun newInstance() = PopularFragment()
    }
}