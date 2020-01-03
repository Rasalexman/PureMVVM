package com.rasalexman.core.common.extensions

import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.presentation.BaseFragment
import com.rasalexman.core.presentation.viewModels.IBaseViewModel

///------ INLINE SECTION ----///
inline fun <reified T : IBaseViewModel> BaseFragment<T>.fetch() = this.viewModel?.processViewEvent(
    SEvent.Fetch
)

inline fun <reified T : IBaseViewModel, R : Any> BaseFragment<T>.fetchWith(data: R) = this.viewModel?.processViewEvent(
    SEvent.FetchWith(data)
)

fun <R : Any> R.toFetch() = SEvent.FetchWith(this)

inline fun <reified T : IBaseViewModel> BaseFragment<T>.refresh() =
    this.viewModel?.processViewEvent(SEvent.Refresh)

inline fun <reified I : Any> BaseFragment<*>.save(data: I) {
    this.viewModel?.processViewEvent(SEvent.SEventWithData.Save(data))
}

inline fun <reified T : IBaseViewModel, reified I : Any> BaseFragment<T>.update(data: T) =
    this.viewModel?.processViewEvent(
        SEvent.SEventWithData.Update(data)
    )

inline fun <reified T : IBaseViewModel, reified I : Any> BaseFragment<T>.delete(data: T) =
    this.viewModel?.processViewEvent(
        SEvent.SEventWithData.Delete(data)
    )