package com.rasalexman.puremvvm.presentation.tabs.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.mikepenz.fastadapter.paged.ExperimentalPagedSupport
import com.rasalexman.core.common.extensions.fetchWith
import com.rasalexman.core.common.extensions.toFetch
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.presentation.ISearchableFragment
import com.rasalexman.core.presentation.recyclerview.BaseToolbarRecyclerFragment
import com.rasalexman.core.presentation.holders.BaseRecyclerUI
import com.rasalexman.core.presentation.recyclerview.paged.BaseToolbarPagedRecyclerFragment
import com.rasalexman.core.presentation.viewModels.IBaseViewModel
import com.rasalexman.providers.data.models.local.MovieEntity
import com.rasalexman.puremvvm.R
import com.rasalexman.tabhome.presentation.movieslist.MovieItemUI
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalPagedSupport
class SearchListFragment : BaseToolbarPagedRecyclerFragment<MovieEntity, MovieItemUI, SearchViewModel>(), ISearchableFragment {


    override val viewModel: SearchViewModel by viewModels()

    private var searchView: SearchView? = null
    private var searchMenuItem: MenuItem? = null

    override val asyncDifferConfig: AsyncDifferConfig<MovieEntity> by unsafeLazy {
        AsyncDifferConfig.Builder<MovieEntity>(object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
                oldItem == newItem
        }).build()
    }

    override val placeholderInterceptor: (Int) -> MovieItemUI = {
        MovieItemUI.createPlaceHolderItem()
    }

    override val interceptor: (MovieEntity) -> MovieItemUI = {
        it.run {
            MovieItemUI(
                id = id,
                voteCount = voteCount,
                voteAverage = voteAverage,
                isVideo = isVideo,
                title = title,
                popularity = popularity,
                posterPath = posterPath.takeIf { it.isNotEmpty() } ?: backdropPath,
                originalLanguage = originalLanguage,
                originalTitle = originalTitle,
                genreIds = genreIds,
                backdropPath = backdropPath,
                releaseDate = releaseDate.takeIf { dt ->
                    dt > 0L
                }?.let {
                    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(releaseDate))
                }.orEmpty(),
                adult = adult,
                overview = overview
            )
        }
    }

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
        query?.let(::fetchWith)
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