package com.rasalexman.core.presentation.recyclerview.paged

import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mikepenz.fastadapter.paged.ExperimentalPagedSupport
import com.rasalexman.core.R
import com.rasalexman.core.common.extensions.onResultChange
import com.rasalexman.core.common.typealiases.AnyResultLiveData
import com.rasalexman.core.presentation.holders.BaseRecyclerUI
import com.rasalexman.core.presentation.viewModels.IBaseViewModel
import kotlinx.android.synthetic.main.layout_refresh_recycler.*

@ExperimentalPagedSupport
abstract class BasePagedRefreshRecyclerFragment<Item : BaseRecyclerUI<*>, out VM: IBaseViewModel> : BasePagedRecyclerFragment<Item, VM>(), SwipeRefreshLayout.OnRefreshListener {

    override val layoutId: Int
        get() = R.layout.layout_refresh_recycler

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // for swipe to refresh handler
        onResultChange(viewModel?.anyLiveData as? AnyResultLiveData, ::onResultHandler)
        (refreshLayout as SwipeRefreshLayout).setOnRefreshListener(this)
    }

    override fun onRefresh() {
        (refreshLayout as SwipeRefreshLayout).isRefreshing = false
    }

    override fun onDestroyView() {
        (refreshLayout as SwipeRefreshLayout).setOnRefreshListener(null)
        super.onDestroyView()
    }

    override fun hideLoading() {
        (refreshLayout as SwipeRefreshLayout).isRefreshing = false
        super.hideLoading()
    }
}