package com.rasalexman.tabevents.presentation.popular

import androidx.fragment.app.viewModels
import com.mikepenz.fastadapter.paged.ExperimentalPagedSupport
import com.rasalexman.core.common.extensions.refresh
import com.rasalexman.core.presentation.recyclerview.paged.BasePagedRefreshRecyclerFragment
import com.rasalexman.models.ui.MovieItemUI

@ExperimentalPagedSupport
class PopularFragment : BasePagedRefreshRecyclerFragment<MovieItemUI, PopularViewModel>() {

    override val viewModel: PopularViewModel by viewModels()

    override val placeholderInterceptor: (Int) -> MovieItemUI = {
        MovieItemUI.createPlaceHolderItem()
    }

    override fun onRefresh() {
        refresh()
    }

    companion object {
        fun newInstance() = PopularFragment()
    }
}