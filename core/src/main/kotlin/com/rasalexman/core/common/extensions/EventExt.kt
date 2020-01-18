package com.rasalexman.core.common.extensions

import com.rasalexman.core.data.dto.Fetch
import com.rasalexman.core.data.dto.FetchWith
import com.rasalexman.core.data.dto.Refresh
import com.rasalexman.core.presentation.BaseFragment
import com.rasalexman.core.presentation.viewModels.IBaseViewModel

///------ INLINE SECTION ----///
inline fun <reified T : IBaseViewModel> BaseFragment<T>.fetch() =
    this.viewModel?.processViewEvent(Fetch) ?: Unit

inline fun <reified T : IBaseViewModel, R : Any> BaseFragment<T>.fetchWith(data: R) = this.viewModel?.processViewEvent(
    data.toFetch()
) ?: Unit

fun <R : Any> R.toFetch() = FetchWith(this)

inline fun <reified T : IBaseViewModel> BaseFragment<T>.refresh() =
    this.viewModel?.processViewEvent(Refresh) ?: Unit