package com.rasalexman.providers.data.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GenreEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val images: MutableList<String> = mutableListOf()
)