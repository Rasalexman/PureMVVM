package com.rasalexman.tabevents.presentation.upcoming

import androidx.fragment.app.viewModels
import com.rasalexman.core.common.extensions.fetchWith
import com.rasalexman.core.presentation.recyclerview.BaseRecyclerFragment
import com.rasalexman.tabhome.presentation.movieslist.MovieItemUI

class UpcomingFragment : BaseRecyclerFragment<MovieItemUI, UpcomingViewModel>() {

    override val viewModel: UpcomingViewModel by viewModels()

    override val needScroll: Boolean
        get() = true

    override val onLoadNextPageHandler: ((Int) -> Unit) = {
        fetchWith(it)
    }

    companion object {
        fun newInstance() = UpcomingFragment()
    }
}