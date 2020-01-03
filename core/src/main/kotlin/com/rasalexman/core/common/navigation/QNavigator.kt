package com.rasalexman.core.common.navigation

sealed class QNavigator(val tag: String) {
    object MainNavigator : QNavigator(
        MAIN_NAVIGATOR
    )
    object TabNavigator : QNavigator(
        TAB_NAVIGATOR
    )
    object OnboardingNavigator : QNavigator(
        ONBOARDING_NAVIGATOR
    )
    object AddTaskNavigator : QNavigator(
        ADD_TASK_NAVIGATOR
    )

    companion object {
        const val MAIN_NAVIGATOR: String = "MainNavigator"
        const val TAB_NAVIGATOR: String = "TabNavigator"
        const val ONBOARDING_NAVIGATOR: String = "OnboardingNavigator"
        const val ADD_TASK_NAVIGATOR: String = "AddTaskNavigator"
    }
}
