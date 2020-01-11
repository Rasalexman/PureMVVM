package com.rasalexman.tabprofile.presentation

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import coil.api.load
import de.hdodenhof.circleimageview.CircleImageView


object BindingAdapters {
    @BindingAdapter("app:url")
    @JvmStatic fun loadImage(view: CircleImageView, url: LiveData<String>) {
        if(url.value?.isNotEmpty() == true)
            view.load(url.value)
    }
}
