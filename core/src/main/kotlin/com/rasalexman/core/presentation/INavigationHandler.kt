package com.rasalexman.core.presentation

interface INavigationHandler {
    val currentNavHandler: INavigationHandler?
    fun onSupportNavigateUp(): Boolean
    fun onBackPressed(): Boolean
}