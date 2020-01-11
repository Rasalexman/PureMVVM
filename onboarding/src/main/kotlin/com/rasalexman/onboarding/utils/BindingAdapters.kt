package com.rasalexman.onboarding.utils

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import com.rasalexman.core.common.extensions.string

object BindingAdapters {

    @BindingAdapter("app:validationError")
    @JvmStatic fun validateError(editText: TextInputEditText, validationError: MutableLiveData<Int>) {
        editText.error = validationError.value?.run { editText.context.string(this) }
    }
}