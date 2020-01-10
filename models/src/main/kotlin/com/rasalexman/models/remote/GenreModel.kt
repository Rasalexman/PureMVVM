package com.rasalexman.models.remote

import androidx.annotation.Keep
import com.rasalexman.core.data.base.IConvertableTo
import com.rasalexman.models.local.GenreEntity

@Keep
data class GenreModel(
    val id: Int?,
    val name: String?
) : IConvertableTo<GenreEntity> {
    override fun convertTo(): GenreEntity? {
        return if(this.id != null && !this.name.isNullOrEmpty()) {
            GenreEntity(this.id, this.name)
        } else null
    }
}