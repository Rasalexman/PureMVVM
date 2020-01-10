package com.rasalexman.puremvvm.presentation.tabs.search

import android.app.SearchManager
import android.content.Context
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.mikepenz.fastadapter.paged.ExperimentalPagedSupport
import com.rasalexman.core.common.extensions.fetchWith
import com.rasalexman.core.common.extensions.hideKeyboard
import com.rasalexman.core.common.extensions.showKeyboard
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.presentation.recyclerview.paged.BaseToolbarPagedRecyclerFragment
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.puremvvm.R
import com.rasalexman.tabhome.data.convert
import com.rasalexman.tabhome.presentation.movieslist.MovieItemUI
import kotlinx.android.synthetic.main.fragment_search.*

@ExperimentalPagedSupport
class SearchListFragment : BaseToolbarPagedRecyclerFragment<MovieItemUI, MovieItemUI, SearchViewModel>(), SearchView.OnQueryTextListener {

    override val viewModel: SearchViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_search

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

    override fun initLayout() {
        super.initLayout()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        val searchManager: SearchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setOnQueryTextListener(this)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.setOnQueryTextListener(this@SearchListFragment)

        searchView.postDelayed({
            searchView.requestFocus()
            showKeyboard(searchView)
        }, 500L)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        hideKeyboard()
        query?.let(::fetchWith)
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    override fun onDestroyView() {
        searchView?.setOnQueryTextListener(null)
        super.onDestroyView()
    }
}