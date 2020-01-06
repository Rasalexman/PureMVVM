package com.rasalexman.core.presentation.recyclerview

import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rasalexman.core.R
import com.rasalexman.core.presentation.holders.BaseRecyclerUI
import com.rasalexman.core.presentation.viewModels.IBaseViewModel
import kotlinx.android.synthetic.main.layout_refresh_recycler.*

abstract class BaseRefreshRecyclerFragment<I : BaseRecyclerUI<*>, out VM : IBaseViewModel> :
    BaseRecyclerFragment<I, VM>(), SwipeRefreshLayout.OnRefreshListener {

    override val layoutId: Int
        get() = R.layout.layout_refresh_recycler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (refreshLayout as SwipeRefreshLayout).setOnRefreshListener(this)
    }

    override fun onRefresh() {
        (refreshLayout as SwipeRefreshLayout).isRefreshing = false
    }

    override fun onDestroyView() {
        (refreshLayout as SwipeRefreshLayout).setOnRefreshListener(null)
        super.onDestroyView()
    }
}
