package com.rasalexman.core.presentation.viewpager

import androidx.appcompat.widget.Toolbar
import com.rasalexman.core.R
import com.rasalexman.core.presentation.TabSelectListener
import com.rasalexman.core.presentation.viewModels.IBaseViewModel
import kotlinx.android.synthetic.main.layout_toolbar.*

abstract class BaseToolbarTabViewPagerFragment<out VM : IBaseViewModel> : BaseTabViewPagerFragment<VM>(),
    TabSelectListener {

    override val layoutId: Int
        get() = R.layout.layout_toolbar_tab_viewpager

    override val toolbar: Toolbar?
        get() = toolbarLayout
}
