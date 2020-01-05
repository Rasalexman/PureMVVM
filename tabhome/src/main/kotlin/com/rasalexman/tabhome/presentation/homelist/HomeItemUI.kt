package com.rasalexman.tabhome.presentation.homelist

import android.view.View
import android.widget.ImageView
import androidx.annotation.Keep
import androidx.core.view.forEachIndexed
import coil.api.load
import com.rasalexman.core.common.extensions.clear
import com.rasalexman.core.presentation.holders.BaseRecyclerUI
import com.rasalexman.core.presentation.holders.BaseViewHolder
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUI
import com.rasalexman.providers.BuildConfig
import com.rasalexman.tabhome.R
import kotlinx.android.synthetic.main.layout_item_genre.view.*

@Keep
data class HomeItemUI(
        val id: Int,
        val name: String,
        val images: List<String>
) : BaseRecyclerUI<HomeItemUI.GenreViewHolder>() {

    init {
        identifier = id.toLong()
    }

    override val layoutRes: Int get() = R.layout.layout_item_genre
    override fun getViewHolder(v: View) = GenreViewHolder(v)
    override val type: Int = 1024

    class GenreViewHolder(view: View) : BaseViewHolder<HomeItemUI>(view), ICoroutinesManager {

        override fun bindView(item: HomeItemUI, payloads: MutableList<Any>) {
            with(containerView) {
                titleTextView.text = item.name
                val urlList = item.images

                launchOnUI {
                    imagesLayout.forEachIndexed { index, view ->
                        val imageView = view as ImageView
                        val imageUrl = "${BuildConfig.IMAGES_URL}${urlList[index]}"
                        imageView.load(imageUrl)
                    }
                }
            }
        }

        override fun unbindView(item: HomeItemUI) {
            with(containerView) {
                titleTextView.text = null
                imageView1.clear()
                imageView2.clear()
                imageView3.clear()
            }
        }
    }
}