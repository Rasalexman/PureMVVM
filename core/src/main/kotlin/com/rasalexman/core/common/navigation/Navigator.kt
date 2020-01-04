package com.rasalexman.core.common.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDirections

sealed class Navigator(
    val tag: String,
    val hostController: NavController) {

    class MainNavigator(
        hostController: NavController,
        val showOnboardingDirection: NavDirections,
        val showTabsDirection: NavDirections
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

    companion object {
        const val MAIN_NAVIGATOR: String = "MainNavigator"
        const val TAB_NAVIGATOR: String = "TabNavigator"
        const val ONBOARDING_NAVIGATOR: String = "OnboardingNavigator"
    }
}
