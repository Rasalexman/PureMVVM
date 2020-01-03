package com.rasalexman.core.common.extensions

import androidx.navigation.NavController
import com.mincor.kodi.core.IKodi
import com.mincor.kodi.core.instance
import com.rasalexman.core.common.navigation.QNavigator.Companion.ADD_TASK_NAVIGATOR
import com.rasalexman.core.common.navigation.QNavigator.Companion.MAIN_NAVIGATOR
import com.rasalexman.core.common.navigation.QNavigator.Companion.ONBOARDING_NAVIGATOR
import com.rasalexman.core.common.navigation.QNavigator.Companion.TAB_NAVIGATOR

fun IKodi.mainNavigator(): NavController = instance(MAIN_NAVIGATOR)
fun IKodi.tabNavigator(): NavController = instance(TAB_NAVIGATOR)
fun IKodi.onboardingNavigator(): NavController = instance(ONBOARDING_NAVIGATOR)
fun IKodi.addTaskNavigator(): NavController = instance(ADD_TASK_NAVIGATOR)
