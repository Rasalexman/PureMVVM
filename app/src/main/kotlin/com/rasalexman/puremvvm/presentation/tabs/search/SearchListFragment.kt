package com.rasalexman.puremvvm.presentation.tabs.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import com.rasalexman.core.presentation.ISearchableFragment
import com.rasalexman.core.presentation.recyclerview.BaseToolbarRecyclerFragment
import com.rasalexman.core.presentation.holders.BaseRecyclerUI
import com.rasalexman.core.presentation.viewModels.IBaseViewModel
import com.rasalexman.puremvvm.R

class SearchListFragment : BaseToolbarRecyclerFragment<BaseRecyclerUI<*>, IBaseViewModel>(), ISearchableFragment {

    private var searchView: SearchView? = null
    private var searchMenuItem: MenuItem? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.all_tasks_menu, menu)
        // Associate searchable configuration with the SearchView
        val searchManager: SearchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchMenuItem = menu.findItem(R.id.action_search).apply {
            setOnActionExpandListener(this@SearchListFragment)
            searchView = (this.actionView as SearchView).apply {
                isIconified = false
                setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
                setOnQueryTextListener(this@SearchListFragment)
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //viewModel.searchByQuery(query)
        searchView?.clearFocus()
        return true
    }

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        //viewModel.searchByQuery("")
        return true
    }

    override fun onDestroyView() {
        searchView?.setOnQueryTextListener(null)
        searchMenuItem?.setOnActionExpandListener(null)
        searchView = null
        searchMenuItem = null
        super.onDestroyView()
    }
}