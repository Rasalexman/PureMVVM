package com.rasalexman.core.data.dto

import androidx.navigation.NavController
import androidx.navigation.NavDirections

interface SEvent

object Fetch : SEvent
data class FetchWith<T : Any>(val data: T) : SEvent
object Refresh : SEvent

data class NavigateTo(
    val navDirection: NavDirections,
    val qNavigator: NavController
) : SEvent