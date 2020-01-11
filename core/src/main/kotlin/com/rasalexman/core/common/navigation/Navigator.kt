package com.rasalexman.core.common.navigation

import androidx.navigation.NavController
import com.rasalexman.core.common.typealiases.UnitHandler

sealed class Navigator(
    val tag: String,
    val hostController: NavController) {

    class MainNavigator(
        hostController: NavController,
        val showOnboardingHandler: UnitHandler,
        val showTabsHandler: UnitHandler
    ) : Navigator(
        MAIN_NAVIGATOR,
        hostController
    )
    class TabNavigator(hostController: NavController) : Navigator(
        TAB_NAVIGATOR,
        hostController
    )
    class OnboardingNavigator(hostController: NavController) : Navigator(
        ONBOARDING_NAVIGATOR,
        hostController
    )

    class HomeTabNavigator(hostController: NavController) : Navigator(
        HOME_TAB_NAVIGATOR,
        hostController
    )

    companion object {
        const val MAIN_NAVIGATOR: String = "MainNavigator"
        const val TAB_NAVIGATOR: String = "TabNavigator"
        const val ONBOARDING_NAVIGATOR: String = "OnboardingNavigator"
        const val HOME_TAB_NAVIGATOR: String = "HomeNavigator"
    }
}
