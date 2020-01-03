package com.rasalexman.core.presentation

import com.google.android.material.tabs.TabLayout
import com.rasalexman.core.R
import com.rasalexman.core.presentation.utils.TabLayoutMediator
import com.rasalexman.core.presentation.viewModels.IBaseViewModel
import kotlinx.android.synthetic.main.layout_tab_fixed.*

abstract class BaseTabViewPagerFragment<out VM : IBaseViewModel> : BaseViewPagerFragment<VM>(),
    TabSelectListener {

    override val layoutId: Int
        get() = R.layout.layout_tab_viewpager

    abstract val pageTitles: Array<String>

    open val tabViewLayout: TabLayout
        get() = tabLayout as TabLayout

    private val selectedPosition: Int
        get() = viewModel?.getFromSaveState(KEY_POSITION) ?: DEFAULT_POSITION

    private var selectedTab: TabLayout.Tab? = null
    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun initLayout() {
        super.initLayout()
        tabLayoutMediator = TabLayoutMediator(tabViewLayout, viewPagerLayout) { tab, position ->
            setCustomTabView(tab, position)
        }.apply { attach() }

        viewPagerLayout.setCurrentItem(selectedPosition, false)
        tabViewLayout.addOnTabSelectedListener(this)
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        viewModel?.addToSaveState(KEY_POSITION, tab.position)
        selectTab(tab)
    }

    protected open fun selectTab(tab: TabLayout.Tab) {
        unSelectTab(selectedTab)
        selectedTab = tab
    }

    protected open fun unSelectTab(tab: TabLayout.Tab?) = Unit
    protected open fun setCustomTabView(tab: TabLayout.Tab, position: Int) = Unit

    override fun onDestroyView() {
        selectedTab = null
        tabLayoutMediator?.detach()
        tabLayoutMediator = null
        super.onDestroyView()
    }

    companion object {
        private const val KEY_POSITION = "SELECTED_POSITION"
        private const val DEFAULT_POSITION = 0
    }
}
