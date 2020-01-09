package com.rasalexman.tabhome.presentation.genreslist

import android.view.View
import androidx.annotation.Keep
import coil.api.load
import com.rasalexman.core.presentation.holders.BaseRecyclerUI
import com.rasalexman.core.presentation.holders.BaseViewHolder
import com.rasalexman.coroutinesmanager.CoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUI
import com.rasalexman.tabhome.R
import kotlinx.android.synthetic.main.layout_item_genre.*

@Keep
data class GenreItemUI(
    val id: Int,
    val name: String,
    val images: List<String>
) : BaseRecyclerUI<GenreItemUI.GenreViewHolder>() {

    init {
        identifier = id.toLong()
    }

    override val layoutRes: Int get() = R.layout.layout_item_genre
    override fun getViewHolder(v: View) = GenreViewHolder(v)
    override val type: Int = 1024

    class GenreViewHolder(view: View) : BaseViewHolder<GenreItemUI>(view) {
        private val coroutinesManager = CoroutinesManager()

        override fun bindView(item: GenreItemUI, payloads: MutableList<Any>) {
            titleGenreTextView.text = item.name
            val urlList = item.images
            coroutinesManager.launchOnUI {
                imageView1.load(urlList.getOrNull(0))
                imageView2.load(urlList.getOrNull(1))
                imageView3.load(urlList.getOrNull(2))
            }
        }
    }
}