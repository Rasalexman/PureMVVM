package com.rasalexman.tabevents.presentation.toprated

import androidx.fragment.app.viewModels
import com.rasalexman.core.common.extensions.fetchWith
import com.rasalexman.core.presentation.recyclerview.BaseRecyclerFragment
import com.rasalexman.models.ui.MovieItemUI

class TopRatedFragment : BaseRecyclerFragment<MovieItemUI, TopRatedViewModel>() {

    override val viewModel: TopRatedViewModel by viewModels()

    override val needScroll: Boolean
        get() = true

    override val onLoadNextPageHandler: ((Int) -> Unit) = {
        fetchWith(it)
    }

    companion object {
        fun newInstance() = TopRatedFragment()
    }
}