package com.rasalexman.core.data.dto

interface SEvent

object Fetch : SEvent
data class FetchWith<T : Any>(val data: T) : SEvent
object Refresh : SEvent
