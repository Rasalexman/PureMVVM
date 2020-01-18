package com.rasalexman.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mincor.kodi.core.IKodi
import com.rasalexman.core.R
import com.rasalexman.core.common.extensions.*
import com.rasalexman.core.common.typealiases.AnyResultLiveData
import com.rasalexman.core.common.typealiases.InHandler
import com.rasalexman.core.common.typealiases.UnitHandler
import com.rasalexman.core.data.dto.SEvent
import com.rasalexman.core.data.dto.SResult
import com.rasalexman.core.presentation.viewModels.IBaseViewModel
import com.rasalexman.coroutinesmanager.ICoroutinesManager
import com.rasalexman.coroutinesmanager.launchOnUITryCatch
import kotlinx.android.synthetic.main.layout_toolbar.*

abstract class BaseFragment<out VM : IBaseViewModel> : Fragment(),
    INavigationHandler, IKodi, ICoroutinesManager {

    /**
     * Layout Resource Id [LayoutRes]
     */
    abstract val layoutId: Int

    /**
     * Content Layout Id
     */
    open val contentViewLayout: View? = null

    /**
     * Loading Layout id
     */
    open val loadingViewLayout: View? = null

    /**
     * Toolbar title
     */
    protected open val toolbarTitle: String = ""

    /**
     * Need to center toolbar title
     */
    protected open val centerToolbarTitle: Boolean = false

    /**
     * Toolbar instance
     */
    protected open val toolbar: Toolbar? = null

    /**
     * Can this fragment navigate back or can be pressed back button
     */
    open val canGoBack: Boolean = false

    /**
     * Fragment ViewModel instance
     */
    open val viewModel: VM? = null

    /**
     * Does this fragment need toolbar back button
     */
    protected open val needBackButton: Boolean = false

    /**
     * Current [INavigationHandler] for check in Main activity class
     */
    override val currentNavHandler: INavigationHandler?
        get() = this

    /**
     * Current Navigation Controller
     */
    override val navController: NavController
        get() = this.findNavController()

    /**
     * when need to create view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addResultLiveDataObservers()
        addErrorLiveDataObservers()
        addAnyLiveDataObservers()

        showToolbar()
        initLayout()
    }

    protected open val onAnyLiveDataHandler: InHandler<Any>? = null

    /**
     * Add Standard Live data Observers to handler [SResult] event
     */
    protected open fun addAnyLiveDataObservers() {
        onAnyChange(viewModel?.anyLiveData, onAnyLiveDataHandler)
    }

    /**
     * Add Standard Live data Observers to handler [SResult] event
     */
    @Suppress("UNCHECKED_CAST")
    protected open fun addResultLiveDataObservers() {
        onResultChange(viewModel?.resultLiveData as? AnyResultLiveData, ::onResultHandler)
    }

    /**
     * Add Observer for Error Live Data handles (ex. from CoroutinesManager)
     */
    protected open fun addErrorLiveDataObservers() {
        onResultChange(viewModel?.errorLiveData, ::onResultHandler)
    }

    /**
     * For Add Custom Toolbar instance as SupportActionBar
     */
    protected open fun showToolbar() {
        toolbar?.let { toolbar ->
            if (toolbarTitle.isNotEmpty()) {
                if (centerToolbarTitle) {
                    toolbarTitleTextView.show()
                    toolbarTitleTextView.text = toolbarTitle
                    if (needBackButton) {
                        val lp = toolbarTitleTextView.layoutParams as Toolbar.LayoutParams
                        lp.marginEnd = dimen(R.dimen.size_48dp)
                        toolbarTitleTextView.layoutParams = lp
                    }
                } else {
                    toolbarTitleTextView.hide()
                    toolbar.title = toolbarTitle
                }
            }
            (activity as? AppCompatActivity)?.let { activityCompat ->
                activityCompat.setSupportActionBar(toolbar)
                if (needBackButton) {
                    activityCompat.supportActionBar?.apply {
                        setDisplayHomeAsUpEnabled(true)
                        setHomeButtonEnabled(true)
                        elevation = dimen(R.dimen.size_6dp).toFloat()
                    }
                    toolbar.setNavigationOnClickListener {
                        onBackPressed()
                    }
                }
            }
        }
    }

    /**
     * Support function to implement to set view Listeners (ex. button.setOnClickListener)
     */
    open fun initLayout() = Unit

    /**
     * Show alert dialog for [SResult.ErrorResult.Alert]
     */
    protected open fun showAlertDialog(
        message: Any,
        okTitle: Int = R.string.title_try_again,
        okHandler: UnitHandler? = null
    ) {
        hideKeyboard()
        hideLoading()
        alert(message = message, okTitle = okTitle, okHandler = okHandler)
    }

    /**
     * Show toast message for [SResult.ErrorResult.Error]
     */
    protected open fun showToast(message: Any, interval: Int = Toast.LENGTH_SHORT) {
        hideKeyboard()
        hideLoading()
        toast(message, interval)
    }

    /**
     * Show loading state for [SResult.Loading]
     */
    open fun showLoading() = launchOnUITryCatch(tryBlock = {
        hideKeyboard()
        loadingViewLayout?.show()
        contentViewLayout?.hide()
    }, catchBlock = {
        log { "Cannot perform action: `showLoading` with $this@BaseFragment. Error ${it.message}" }
    })

    open fun hideLoading() = launchOnUITryCatch(tryBlock = {
        hideKeyboard()
        loadingViewLayout?.hide()
        contentViewLayout?.show()
    }, catchBlock = {
        log { "Cannot perform action: `hideLoading` with $this@BaseFragment. Error ${it.message}" }
    })

    /**
     * When pressed back
     */
    override fun onBackPressed(): Boolean {
        return if (!canGoBack) true else navController.popBackStack()
    }

    /**
     * For support navigation handle
     */
    override fun onSupportNavigateUp(): Boolean {
        return true
    }

    /**
     * When view destroy
     */
    override fun onDestroyView() {
        context?.closeAlert()
       // (view as? ViewGroup)?.clear()
        super.onDestroyView()
    }

    /**
     * Base [SResult] handle function
     */
    protected open fun onResultHandler(result: SResult<*>) {
        if (result.isHandled) return
        result.handle()

        when (result) {
            is SResult.Success -> hideLoading()
            is SResult.Loading -> showLoading()

            is SResult.NavigateResult.NavigateTo -> {
                val (direction, navigator) = result
                (navigator ?: navController).navigate(direction)
            }

            is SResult.NavigateResult.NavigateBack -> onBackPressed()

            is SResult.ErrorResult -> {
                result.getMessage()?.let {
                    if (result is SResult.ErrorResult.Error) {
                        showToast(it)
                    } else if (result is SResult.ErrorResult.Alert) {
                        showAlertDialog(it)
                    }
                }
            }
        }
    }

    /**
     * Process [SEvent] to view model
     */
    protected open fun processViewEvent(viewEvent: SEvent) {
        this.viewModel?.processViewEvent(viewEvent)
    }
}
