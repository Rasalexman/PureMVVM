package com.rasalexman.models.ui

import android.view.View
import androidx.annotation.Keep
import androidx.core.view.isVisible
import coil.api.load
import com.rasalexman.core.BuildConfig
import com.rasalexman.core.common.extensions.clear
import com.rasalexman.core.common.extensions.hide
import com.rasalexman.core.common.extensions.show
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.presentation.holders.BaseRecyclerUI
import com.rasalexman.core.presentation.holders.BaseViewHolder
import com.rasalexman.coroutinesmanager.CoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUI
import com.rasalexman.models.R
import kotlinx.android.synthetic.main.layout_item_movies.*

@Keep
data class MovieItemUI(
    val id: Int,
    val voteCount: Int,
    val voteAverage: Double,
    val isVideo: Boolean,
    val title: String,
    val popularity: Double,
    val posterPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val genreIds: List<Int>,
    val backdropPath: String,
    val releaseDate: String,
    val originalReleaseDate: Long,
    val adult: Boolean,
    val overview: String
) : BaseRecyclerUI<MovieItemUI.MovieViewHolder>() {

    var isPlaceHolder: Boolean = false

    override val layoutRes: Int = R.layout.layout_item_movies
    override fun getViewHolder(v: View) = MovieViewHolder(v)
    override val type: Int = 1032

    init {
        identifier = id.toLong()
    }

    val fullPosterUrl: String
        get() = "${BuildConfig.IMAGES_URL}$posterPath"

    class MovieViewHolder(view: View) : BaseViewHolder<MovieItemUI>(view) {

        private val coroutinesManager by unsafeLazy { CoroutinesManager() }

        override fun bindView(item: MovieItemUI, payloads: MutableList<Any>) {
            itemView.isVisible = !item.isPlaceHolder

            titleTextView.text = item.title
            releaseTextView.text = item.releaseDate
            overviewTextView.text = item.overview
            setVoteAverage(item)

            item.fullPosterUrl.takeIf {
                !item.isPlaceHolder
            }?.let { imageUrl ->
                coroutinesManager.launchOnUI {
                    movieImageView.load(imageUrl) {
                        placeholder(R.drawable.ic_cloud_off_black_24dp)
                        target(onStart = {
                            imageProgressBar.show()
                        }, onSuccess = {
                            movieImageView.setImageDrawable(it)
                            imageProgressBar.hide(true)
                        }, onError = {
                            movieImageView.setImageResource(R.drawable.ic_cloud_off_black_24dp)
                            imageProgressBar.hide(true)
                        })
                    }
                }
            }
        }

        override fun unbindView(item: MovieItemUI) {
            titleTextView.clear()
            releaseTextView.clear()
            overviewTextView.clear()
            voteAverageTextView.clear()
            movieImageView.clear()
            imageProgressBar.hide()
        }

        private fun setVoteAverage(item: MovieItemUI) {
            if (item.voteAverage > 0.0) {
                voteAverageTextView.show()
                voteAverageTextView.text = item.voteAverage.toString()
            } else {
                voteAverageTextView.hide()
            }
        }
    }

    companion object {
        private val moviePlaceholder by unsafeLazy {
            MovieItemUI(
                0, 0, 0.0, false, "",
                0.0, "", "", "",
                emptyList(), "", "", 0L,false, ""
            ).apply {
                isPlaceHolder = true
            }
        }

        fun createPlaceHolderItem() = moviePlaceholder
    }
}