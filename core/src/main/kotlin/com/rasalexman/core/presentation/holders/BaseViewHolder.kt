package com.rasalexman.core.presentation.holders

import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.FastAdapter
import com.rasalexman.core.common.extensions.clear
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder<I : BaseRecyclerUI<*>>(
    override val containerView: View
) : FastAdapter.ViewHolder<I>(containerView), LayoutContainer {
    override fun unbindView(item: I) {
        (containerView as? ViewGroup)?.clear()
    }
}