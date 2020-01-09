package com.rasalexman.tabevents.presentation

import android.view.LayoutInflater
import com.google.android.material.tabs.TabLayout
import com.mikepenz.fastadapter.paged.ExperimentalPagedSupport
import com.rasalexman.core.common.extensions.color
import com.rasalexman.core.common.extensions.drawable
import com.rasalexman.core.common.extensions.string
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.presentation.BaseFragment
import com.rasalexman.core.presentation.viewModels.IBaseViewModel
import com.rasalexman.core.presentation.viewpager.BaseToolbarTabViewPagerFragment
import com.rasalexman.tabevents.R
import com.rasalexman.tabevents.presentation.popular.PopularFragment
import com.rasalexman.tabevents.presentation.toprated.TopRatedFragment
import com.rasalexman.tabevents.presentation.upcoming.UpcomingFragment
import kotlinx.android.synthetic.main.layout_item_tab.view.*

@ExperimentalPagedSupport
class EventsListFragment : BaseToolbarTabViewPagerFragment<IBaseViewModel>() {

    override val pageTitles: Array<String>
        get() = resources.getStringArray(R.array.events_title)

    override val pages: List<BaseFragment<*>>
        get() = listOf(PopularFragment.newInstance(), TopRatedFragment.newInstance(), UpcomingFragment.newInstance())

    override val toolbarTitle: String
        get() = string(R.string.title_events)

    override val centerToolbarTitle: Boolean
        get() = true

    private val tabBackground by unsafeLazy {
        drawable(R.drawable.bg_tab_selected)
    }

    private val selectedTabColor by unsafeLazy {
        color(R.color.color_white)
    }
    private val unselectedTabColor by unsafeLazy {
        color(R.color.text_secondary)
    }

    override fun setCustomTabView(tab: TabLayout.Tab, position: Int) {
        val tabView = LayoutInflater.from(context).inflate(R.layout.layout_item_tab, null)
        tabView.tabTextView.text = pageTitles[position]
        tab.customView = tabView
        if (selectedPosition == position) selectTab(tab)
    }

    override fun selectTab(tab: TabLayout.Tab) {
        super.selectTab(tab)
        tab.customView?.tabTextView?.apply {
            setTextColor(selectedTabColor)
            background = tabBackground
        }
    }

    override fun unSelectTab(tab: TabLayout.Tab?) {
        tab?.customView?.tabTextView?.apply {
            setTextColor(unselectedTabColor)
            background = null
        }
    }
}