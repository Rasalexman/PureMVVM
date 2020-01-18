package com.rasalexman.core.presentation

interface IBaseHost {
    val navControllerId: Int
    val navigatorTag: String?

    fun unbindNavController()
    fun bindNavController()
}