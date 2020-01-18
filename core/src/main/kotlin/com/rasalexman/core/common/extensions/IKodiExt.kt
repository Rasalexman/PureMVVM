package com.rasalexman.core.common.extensions

import com.mincor.kodi.core.IKodi
import com.mincor.kodi.core.instance
import com.rasalexman.core.common.navigation.Navigator
import com.rasalexman.core.common.navigation.Navigator.Companion.HOME_TAB_NAVIGATOR
import com.rasalexman.core.common.navigation.Navigator.Companion.MAIN_NAVIGATOR
import com.rasalexman.core.common.navigation.Navigator.Companion.ONBOARDING_NAVIGATOR
import com.rasalexman.core.common.navigation.Navigator.Companion.TAB_NAVIGATOR

fun IKodi.mainNavigator(): Navigator.MainNavigator = instance(MAIN_NAVIGATOR)
fun IKodi.tabNavigator(): Navigator.TabNavigator = instance(TAB_NAVIGATOR)
fun IKodi.homeTabNavigator(): Navigator.HomeTabNavigator = instance(HOME_TAB_NAVIGATOR)
fun IKodi.onboardingNavigator(): Navigator.OnboardingNavigator = instance(ONBOARDING_NAVIGATOR)
