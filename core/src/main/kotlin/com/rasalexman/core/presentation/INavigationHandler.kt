package com.rasalexman.core.presentation

import androidx.navigation.NavController

interface INavigationHandler {
    val currentNavHandler: INavigationHandler?
    val navController: NavController?
    fun onSupportNavigateUp(): Boolean
    fun onBackPressed(): Boolean
}