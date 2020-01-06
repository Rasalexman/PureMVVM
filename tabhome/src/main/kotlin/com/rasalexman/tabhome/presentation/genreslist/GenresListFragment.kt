package com.rasalexman.tabhome.presentation.genreslist

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rasalexman.core.common.extensions.ScrollPosition
import com.rasalexman.core.common.extensions.homeTabNavigator
import com.rasalexman.core.common.extensions.string
import com.rasalexman.core.presentation.recyclerview.BaseToolbarRecyclerFragment
import com.rasalexman.tabhome.R

class GenresListFragment : BaseToolbarRecyclerFragment<GenreItemUI, GenresViewModel>() {

    override val viewModel: GenresViewModel by viewModels()

    override val toolbarTitle: String
        get() = string(R.string.title_tab_home)

    override val centerToolbarTitle: Boolean
        get() = true

    override val previousPosition: ScrollPosition
        get() = scrollPosition

    override val onItemClickHandler: ((GenreItemUI) -> Unit) = { genreItem ->
        homeTabNavigator().hostController.navigate(
            GenresListFragmentDirections.showMoviesListFragment(
                itemName = genreItem.name,
                itemId = genreItem.id
            )
        )
    }

    companion object {
        private val scrollPosition = ScrollPosition()
    }
}