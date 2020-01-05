package com.rasalexman.core.presentation

import androidx.navigation.NavController

interface IBaseHost {
    val navControllerId: Int
    val navigatorTag: String?
    val navigatorController: NavController

    fun unbindNavController()
    fun bindNavController()
}