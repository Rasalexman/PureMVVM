package com.rasalexman.tabevents.presentation.upcoming

import com.rasalexman.core.presentation.recyclerview.BaseRecyclerFragment
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.tabhome.presentation.movieslist.MovieItemUI

class UpcomingFragment : BaseRecyclerFragment<MovieItemUI, BaseViewModel>() {

    companion object {
        fun newInstance() = UpcomingFragment()
    }
}