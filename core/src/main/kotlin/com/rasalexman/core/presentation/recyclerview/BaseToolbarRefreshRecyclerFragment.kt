package com.rasalexman.core.presentation.recyclerview

import androidx.appcompat.widget.Toolbar
import com.rasalexman.core.R
import com.rasalexman.core.presentation.holders.BaseRecyclerUI
import com.rasalexman.core.presentation.viewModels.IBaseViewModel
import kotlinx.android.synthetic.main.layout_toolbar.*

abstract class BaseToolbarRefreshRecyclerFragment<I : BaseRecyclerUI<*>, out VM: IBaseViewModel> : BaseRefreshRecyclerFragment<I, VM>() {

    override val canGoBack: Boolean
        get() = needBackButton

    override val layoutId: Int
        get() = R.layout.layout_toolbar_refresh_recycler

    override val toolbar: Toolbar?
        get() = toolbarLayout
}