package com.rasalexman.models.local

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rasalexman.core.data.base.IConvertableTo
import com.rasalexman.models.ui.GenreItemUI

@Keep
@Entity
data class GenreEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val images: MutableList<String> = mutableListOf()
) : IConvertableTo<GenreItemUI> {
    override fun convertTo(): GenreItemUI {
        return GenreItemUI(id = this.id, name = this.name, images = this.images)
    }
}