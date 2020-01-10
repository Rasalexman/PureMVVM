package com.rasalexman.core.presentation.recyclerview

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import com.mikepenz.fastadapter.items.AbstractItem
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

abstract class BaseRecyclerFragment<Item : BaseRecyclerUI<*>, out VM : IBaseViewModel> : BaseFragment<VM>() {

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
    protected open val layoutManagerOrientation: Int = LinearLayoutManager.VERTICAL
    // бесконечный слушатель слушатель скролла
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    // custom decorator
    protected open var itemDecoration: RecyclerView.ItemDecoration? = null

    // main adapter items holder
    protected open val itemAdapter: ItemAdapter<Item> by unsafeLazy { ItemAdapter<Item>() }

    // footer adapter
    protected open val footerAdapter: ItemAdapter<AbstractItem<*>> by unsafeLazy { ItemAdapter<AbstractItem<*>>() }

    // save our FastAdapter
    protected open val mFastItemAdapter: FastAdapter<AbstractItem<*>> by unsafeLazy {
        FastAdapter.with(listOf(itemAdapter, footerAdapter))
    }

    // последняя сохраненная позиция (index & offset) прокрутки ленты
    protected open val previousPosition: ScrollPosition? = null
    // крутилка прогресса)
    private val progressItem by unsafeLazy { ProgressItem().apply { isEnabled = false } }
    // корличесвто элементов до того как пойдет запрос на скролл пагинацию
    protected open val visibleScrollCount = 5
    // Need scroll handler
    protected open val needScroll: Boolean = false

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

    open fun showItems(list: List<Item>) {
        hideLoading()
        if (list.isNotEmpty()) {
            itemAdapter.set(list)
            //FastAdapterDiffUtil[itemAdapter] = list
            applyScrollPosition()
        }
    }

    open fun addNewItems(list: List<Item>) {
        hideLoading()
        if (list.isNotEmpty()) {
            FastAdapterDiffUtil[itemAdapter] = FastAdapterDiffUtil.calculateDiff(itemAdapter, list)
            scrollToTop()
        }
    }

    open fun addItems(list: List<Item>) {
        hideLoading()
        if (list.isNotEmpty()) {
            itemAdapter.add(list)
        }
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
        clearAdapter()
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
    override fun onResultHandler(result: SResult<*>) {
        hideLoading()
        when (result) {
            is SResult.Success -> {
                if(itemAdapter.adapterItemCount == 0)
                    (result.data as? List<Item>)?.let(::showItems)
                else (result.data as? List<Item>)?.let(::addItems)
            }
            is SResult.Clear -> clearAdapter()
            else -> super.onResultHandler(result)
        }
    }
}
