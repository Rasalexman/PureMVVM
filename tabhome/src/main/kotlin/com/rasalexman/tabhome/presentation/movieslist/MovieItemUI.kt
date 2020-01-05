package com.rasalexman.tabhome.presentation.movieslist

import android.view.View
import androidx.annotation.Keep
import coil.api.load
import com.rasalexman.core.common.extensions.clear
import com.rasalexman.core.common.extensions.hide
import com.rasalexman.core.common.extensions.show
import com.rasalexman.core.presentation.holders.BaseRecyclerUI
import com.rasalexman.core.presentation.holders.BaseViewHolder
import com.rasalexman.providers.BuildConfig
import com.rasalexman.tabhome.R
import kotlinx.android.synthetic.main.layout_item_movies.view.*

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
    val adult: Boolean,
    val overview: String
) : BaseRecyclerUI<MovieItemUI.MovieViewHolder>() {

    override val layoutRes: Int = R.layout.layout_item_movies
    override fun getViewHolder(v: View) = MovieViewHolder(v)
    override val type: Int = 1032

    init {
        identifier = id.toLong()
    }

    val fullPosterUrl: String
        get() = "${BuildConfig.IMAGES_URL}$posterPath"

    class MovieViewHolder(view: View) : BaseViewHolder<MovieItemUI>(view) {

        override fun bindView(item: MovieItemUI, payloads: MutableList<Any>) {
            with(containerView) {
                titleTextView.text = item.title
                releaseTextView.text = item.releaseDate
                overviewTextView.text = item.overview
                setVoteAverage(item)

                movieImageView.load(item.fullPosterUrl) {
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

        override fun unbindView(item: MovieItemUI) {
            with(containerView) {
                titleTextView.clear()
                releaseTextView.clear()
                overviewTextView.clear()
                voteAverageTextView.clear()
                movieImageView.clear()
                imageProgressBar.hide()
            }
        }

        private fun View.setVoteAverage(item: MovieItemUI) {
            if (item.voteAverage > 0.0) {
                voteAverageTextView.show()
                voteAverageTextView.text = item.voteAverage.toString()
            } else {
                voteAverageTextView.hide()
            }
        }
    }
}