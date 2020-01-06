package com.rasalexman.puremvvm.presentation.tabs.events.popular

import com.rasalexman.core.presentation.recyclerview.BaseRecyclerFragment
import com.rasalexman.core.presentation.viewModels.BaseViewModel
import com.rasalexman.tabhome.presentation.movieslist.MovieItemUI

class PopularFragment : BaseRecyclerFragment<MovieItemUI, BaseViewModel>() {

    companion object {
        fun newInstance() = PopularFragment()
    }
}