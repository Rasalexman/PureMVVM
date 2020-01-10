package com.rasalexman.tabhome.presentation.movieslist

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.navigation.fragment.navArgs
import com.mikepenz.fastadapter.paged.ExperimentalPagedSupport
import com.rasalexman.core.common.extensions.toFetch
import com.rasalexman.core.presentation.recyclerview.paged.BaseToolbarPagedRecyclerFragment
import kotlinx.coroutines.launch

@ExperimentalPagedSupport
class MoviesListFragment : BaseToolbarPagedRecyclerFragment<MovieItemUI, MoviesViewModel>() {

    private val moviesNavArgs: MoviesListFragmentArgs by navArgs()

    override val viewModel: MoviesViewModel by viewModels()

    override val toolbarTitle: String
        get() = moviesNavArgs.itemName

    override val needBackButton: Boolean
        get() = true

    override val placeholderInterceptor: (Int) -> MovieItemUI = {
        MovieItemUI.createPlaceHolderItem()
    }

    init {
        lifecycleScope.launch {
            whenCreated {
                processViewEvent(moviesNavArgs.itemId.toFetch())
            }
        }
    }
}