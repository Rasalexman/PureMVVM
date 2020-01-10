package com.rasalexman.core.presentation.recyclerview.paged

import android.os.Bundle
import android.view.View
import androidx.paging.PagedList
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fastadapter.paged.ExperimentalPagedSupport
import com.mikepenz.fastadapter.paged.PagedModelAdapter
import com.mikepenz.fastadapter.ui.items.ProgressItem
import com.rasalexman.core.R
import com.rasalexman.core.common.extensions.ScrollPosition
import com.rasalexman.core.common.extensions.hideKeyboard
import com.rasalexman.core.common.extensions.unsafeLazy
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.presentation.BaseFragment
import com.rasalexman.core.presentation.holders.BaseRecyclerUI
import com.rasalexman.core.presentation.utils.EndlessRecyclerViewScrollListener
import com.rasalexman.core.presentation.viewModels.IBaseViewModel

@ExperimentalPagedSupport
abstract class BasePagedRecyclerFragment<Item : BaseRecyclerUI<*>, out VM : IBaseViewModel> : BaseFragment<VM>() {

    open val recyclerViewId: Int
        get() = R.id.recyclerView

    override val layoutId: Int
        get() = R.layout.layout_recycler

    private var isLoading: Boolean = false

    protected var recycler: RecyclerView? = null

    // current iterating item
    protected var currentItem: Item? = null

    // layout manager for recycler
    protected open var recyclerLayoutManager: RecyclerView.LayoutManager? = null
    // направление размещения элементов в адаптере
    protected open val layoutManagerOrientation  by unsafeLazy { LinearLayoutManager.VERTICAL }
    // бесконечный слушатель слушатель скролла
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    // custom decorator
    protected open var itemDecoration: RecyclerView.ItemDecoration? = null

    open val asyncDifferConfig: AsyncDifferConfig<Item> by unsafeLazy {
        AsyncDifferConfig.Builder<Item>(object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) =
                oldItem.identifier == newItem.identifier
            override fun areContentsTheSame(oldItem: Item, newItem: Item) =
                oldItem == newItem
        }).build()
    }
    abstract val placeholderInterceptor: (Int) -> Item
    private val itemAdapter: PagedModelAdapter<Item, Item> by unsafeLazy {
        PagedModelAdapter(asyncDifferConfig, placeholderInterceptor, { it })
    }

    // footer adapter
    protected open val footerAdapter: ItemAdapter<AbstractItem<*>> by unsafeLazy { ItemAdapter<AbstractItem<*>>() }

    // save our FastAdapter
    protected open val mFastItemAdapter: FastAdapter<*> by unsafeLazy {
        FastAdapter.with(listOf(itemAdapter, footerAdapter)).apply {
            registerTypeInstance(placeholderInterceptor.invoke(0))
        }
    }

    // последняя сохраненная позиция (index & offset) прокрутки ленты
    protected open val previousPosition: ScrollPosition? = null
    // крутилка прогресса)
    private val progressItem by unsafeLazy { ProgressItem().apply { isEnabled = false } }
    // корличесвто элементов до того как пойдет запрос на скролл пагинацию
    protected open val visibleScrollCount  by unsafeLazy { 5 }
    // Need scroll handler
    protected open val needScroll by unsafeLazy { false }

    //
    open val onLoadNextPageHandler: ((Int) -> Unit)? = null
    open val onLoadNextHandler: (() -> Unit)? = null
    open val onItemClickHandler: ((Item) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createRecyclerView(view)
        setRVLayoutManager() // менеджер лайаута
        setItemDecorator() // декорации
        setRVCAdapter() // назначение
        addClickListener()
        addEventHook() // для нажатия внутри айтемов
        if (needScroll) {
            setRVCScroll()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        savePreviousPosition()
        clearFastAdapter()
        clearRecycler()

        recyclerLayoutManager = null
        scrollListener = null
        itemDecoration = null
        recycler = null
        scrollListener = null
        currentItem = null
    }

    override fun showLoading() {
        hideKeyboard()
        hideLoading()
        footerAdapter.add(progressItem)
        isLoading = true
    }

    override fun hideLoading() {
        footerAdapter.clear()
        isLoading = false
    }

    open fun addItems(list: PagedList<Item>) {
        hideLoading()
        itemAdapter.submitList(list)
    }

    open fun showError(message: String?) {
        currentItem = null
        hideLoading()
        message?.let { errorMessage ->
            showToast(errorMessage)
        }
    }

    protected open fun createRecyclerView(view: View) {
        recycler = view.findViewById(recyclerViewId)
    }

    // менеджер лайаута
    protected open fun setRVLayoutManager() {
        recycler?.apply {
            recyclerLayoutManager = LinearLayoutManager(context, layoutManagerOrientation, false)
            layoutManager = recyclerLayoutManager
        }
    }

    // промежутки в адаптере
    protected open fun setItemDecorator() {
        if (recycler?.itemDecorationCount == 0) {
            itemDecoration?.let { recycler?.addItemDecoration(it) }
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun addClickListener() {
        mFastItemAdapter.onClickListener = { _, _, item, _ ->
            (item as? Item)?.let {
                onItemClickHandler?.invoke(it)
            }
            false
        }
    }

    // назначаем адаптеры
    protected open fun setRVCAdapter(isFixedSizes: Boolean = false) {
        recycler?.adapter ?: let {
            recycler?.setHasFixedSize(isFixedSizes)
            recycler?.swapAdapter(mFastItemAdapter, true)
        }
    }

    //
    protected open fun addEventHook() {}

    // слушатель бесконечная прокрутка
    protected open fun setRVCScroll() {
        recycler?.apply {
            scrollListener = scrollListener
                ?: object : EndlessRecyclerViewScrollListener(recyclerLayoutManager, visibleScrollCount) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int) {
                        onLoadNextHandler?.invoke() ?: onLoadNextPageHandler?.invoke(page)
                    }
                }
            addOnScrollListener(scrollListener!!)
        }
    }

    // если хотим сохранить последнюю проскролленную позицию
    protected open fun savePreviousPosition() {
        recycler?.let { rec ->
            previousPosition?.apply {
                index = (rec.layoutManager as? LinearLayoutManager?)?.findFirstVisibleItemPosition() ?: 0
                top = rec.getChildAt(0)?.let { it.top - rec.paddingTop } ?: 0
            }
        }
    }

    // прокручиваем к ранее выбранным элементам
    open fun applyScrollPosition() {
        recycler?.let { rec ->
            stopRecyclerScroll()
            previousPosition?.let {
                (rec.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(it.index, it.top)
            }
        }
    }

    // скролл к верхней записи
    protected open fun scrollToTop() {
        stopRecyclerScroll()
        // сбрасываем прокрутку
        previousPosition?.drop()
        // применяем позицию
        applyScrollPosition()
    }

    private fun stopRecyclerScroll() {
        // останавливаем прокрутку
        recycler?.stopScroll()
    }

    private fun clearAdapter() {
        scrollListener?.resetState()
        itemAdapter.clear()
        mFastItemAdapter.notifyAdapterDataSetChanged()
        mFastItemAdapter.notifyDataSetChanged()
    }

    private fun clearFastAdapter() {
        mFastItemAdapter.apply {
            onClickListener = null
            eventHooks.clear()
        }
    }

    private fun clearRecycler() {
        recycler?.apply {
            removeAllViews()
            removeAllViewsInLayout()
            adapter = null
            layoutManager = null
            itemAnimator = null
            clearOnScrollListeners()
            recycledViewPool.clear()

            itemDecoration?.let {
                removeItemDecoration(it)
            }
            itemDecoration = null
        }
    }

    protected fun removeCurrentItemFromAdapter() {
        currentItem?.let {
            itemAdapter.getAdapterPosition(it).let { position ->
                itemAdapter.remove(position)
                currentItem = null
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> onAnyDataHandler(data: T?) {
        (data as? PagedList<Item>)?.apply(::addItems)
    }

    override fun onResultHandler(result: SResult<*>) {
        hideLoading()
        when (result) {
            is SResult.Clear -> clearAdapter()
            else -> super.onResultHandler(result)
        }
    }
}
