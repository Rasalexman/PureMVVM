package com.rasalexman.core.presentation.holders

import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.items.AbstractItem

abstract class BaseRecyclerUI<VH> : IItem<VH>,
    AbstractItem<VH>() where VH : FastAdapter.ViewHolder<*> {
    override val layoutRes: Int = -1
    override val type: Int =
        TYPE_BASE

    companion object {
        private const val TYPE_BASE = 1
    }
}