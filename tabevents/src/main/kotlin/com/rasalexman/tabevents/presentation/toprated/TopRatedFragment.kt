package com.rasalexman.tabevents.presentation.toprated

import androidx.fragment.app.activityViewModels
import com.rasalexman.core.common.extensions.ScrollPosition
import com.rasalexman.core.common.extensions.fetchWith
import com.rasalexman.core.presentation.recyclerview.BaseRecyclerFragment
import com.rasalexman.models.ui.MovieItemUI

class TopRatedFragment : BaseRecyclerFragment<MovieItemUI, TopRatedViewModel>() {

    override val viewModel: TopRatedViewModel by activityViewModels()

    override val needScroll: Boolean
        get() = true

    override val previousPosition: ScrollPosition
        get() = scrollPosition

    override val onLoadNextPageHandler: ((Int) -> Unit) = {
        fetchWith(it)
    }

    companion object {
        private val scrollPosition = ScrollPosition()
        fun newInstance() = TopRatedFragment()
    }
}