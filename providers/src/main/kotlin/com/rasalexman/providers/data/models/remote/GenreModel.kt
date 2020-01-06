package com.rasalexman.providers.data.models.remote

import com.rasalexman.core.data.base.IConvertableTo
import com.rasalexman.providers.data.models.local.GenreEntity

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