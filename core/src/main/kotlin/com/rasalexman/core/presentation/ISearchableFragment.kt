package com.rasalexman.core.presentation

import android.view.MenuItem
import androidx.appcompat.widget.SearchView

interface ISearchableFragment : MenuItem.OnActionExpandListener, SearchView.OnQueryTextListener {
    override fun onQueryTextChange(newText: String?): Boolean = false
    override fun onMenuItemActionExpand(p0: MenuItem?): Boolean = true
}