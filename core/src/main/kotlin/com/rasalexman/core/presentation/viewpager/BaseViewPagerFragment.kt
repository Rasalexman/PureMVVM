package com.rasalexman.core.presentation.viewpager

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.rasalexman.core.R
import com.rasalexman.core.presentation.BaseFragment
import com.rasalexman.core.presentation.viewModels.IBaseViewModel
import kotlinx.android.synthetic.main.layout_viewpager.*

abstract class BaseViewPagerFragment<out VM : IBaseViewModel> : BaseFragment<VM>() {

    override val layoutId: Int
        get() = R.layout.layout_viewpager

    open val viewPagerLayout: ViewPager2
        get() = viewPager as ViewPager2

    open val viewPagerOrientation: Int
        get() = ViewPager2.ORIENTATION_HORIZONTAL

    abstract val pages: List<BaseFragment<*>>

    override fun initLayout() {
        viewPagerLayout.orientation = viewPagerOrientation
        initViewPagerAdapter()
    }

    protected open fun initViewPagerAdapter() {
        viewPagerLayout.adapter = createViewPagerAdapter()
    }

    protected open fun createViewPagerAdapter(): RecyclerView.Adapter<*> {
        return BaseViewPagerFragmentAdapter(
            pages,
            this
        )
    }

    open class BaseViewPagerFragmentAdapter(
        private val adapterPages: List<BaseFragment<*>>,
        fragment: Fragment
    ) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = adapterPages.size
        override fun createFragment(position: Int): Fragment = adapterPages[position]
    }
}