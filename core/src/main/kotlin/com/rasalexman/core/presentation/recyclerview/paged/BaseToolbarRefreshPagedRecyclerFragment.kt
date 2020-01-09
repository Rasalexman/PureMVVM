package com.rasalexman.core.presentation.recyclerview.paged

import androidx.appcompat.widget.Toolbar
import com.mikepenz.fastadapter.paged.ExperimentalPagedSupport
import com.rasalexman.core.R
import com.rasalexman.core.presentation.holders.BaseRecyclerUI
import com.rasalexman.core.presentation.viewModels.IBaseViewModel
import kotlinx.android.synthetic.main.layout_toolbar.*

@ExperimentalPagedSupport
abstract class BaseToolbarRefreshPagedRecyclerFragment<Entity, I : BaseRecyclerUI<*>, out VM: IBaseViewModel> : BasePagedRefreshRecyclerFragment<Entity, I, VM>() {

    override val canGoBack: Boolean
        get() = needBackButton

    override val layoutId: Int
        get() = R.layout.layout_toolbar_refresh_recycler

    override val toolbar: Toolbar?
        get() = toolbarLayout
}