package com.rasalexman.puremvvm.activity

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.mincor.kodi.core.*
import com.rasalexman.core.common.extensions.hideKeyboard
import com.rasalexman.core.common.navigation.Navigator
import com.rasalexman.core.common.navigation.Navigator.Companion.MAIN_NAVIGATOR
import com.rasalexman.core.presentation.IBaseHost
import com.rasalexman.core.presentation.INavigationHandler
import com.rasalexman.puremvvm.NavigationMainDirections
import com.rasalexman.puremvvm.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IBaseHost, IKodi {

    private val currentNavHandler: INavigationHandler?
        get() = mainHostFragment.childFragmentManager.primaryNavigationFragment as? INavigationHandler

    override val navControllerId: Int
        get() = R.id.mainHostFragment

    override val navigatorTag: String
        get() = MAIN_NAVIGATOR

    override val navigatorController: NavController
        get() = Navigation.findNavController(this@MainActivity, navControllerId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        unbindNavController()
        bindNavController()
    }

    override fun bindNavController() {
        bind<Navigator.MainNavigator>(navigatorTag) with single {
            Navigator.MainNavigator(
                hostController = navigatorController,
                showOnboardingDirection = NavigationMainDirections.showOnboardingFragment(),
                showTabsDirection = NavigationMainDirections.showTabsFragment()
            )
        }
    }

    override fun unbindNavController() {
        unbind<Navigator.MainNavigator>(navigatorTag)
    }

    override fun onSupportNavigateUp(): Boolean {
        return (currentNavHandler?.onSupportNavigateUp() == false && mainHostFragment.findNavController().navigateUp())
    }

    override fun onBackPressed() {
        hideKeyboard()
        if (currentNavHandler?.onBackPressed() == false) {
            super.onBackPressed()
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            when(currentFocus) {
                is EditText,
                is TextInputEditText -> {
                    val outRect = Rect()
                    v?.getGlobalVisibleRect(outRect)
                    if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                        hideKeyboard()
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}
