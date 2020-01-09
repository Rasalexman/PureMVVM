package com.rasalexman.tabevents.presentation.toprated

import com.rasalexman.core.presentation.recyclerview.BaseRecyclerFragment
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.tabhome.presentation.movieslist.MovieItemUI

class TopRatedFragment : BaseRecyclerFragment<MovieItemUI, BaseViewModel>() {

    companion object {
        fun newInstance() = TopRatedFragment()
    }
}