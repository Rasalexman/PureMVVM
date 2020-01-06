package com.rasalexman.tabhome.data

import com.rasalexman.providers.data.models.local.GenreEntity
import com.rasalexman.tabhome.presentation.genreslist.GenreItemUI

fun GenreEntity.convert() = GenreItemUI(id = this.id, name = this.name, images = this.images)