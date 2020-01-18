package com.rasalexman.core.common.extensions

import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.rasalexman.core.common.typealiases.UnitHandler


data class ScrollPosition(var index: Int = 0, var top: Int = 0) {
    fun drop() {
        index = 0
        top = 0
    }
}

fun View.toast(
    message: Any,
    duration: Int = Toast.LENGTH_SHORT
) = context?.toast(message, duration)

fun Fragment.toast(
    message: Any,
    duration: Int = Toast.LENGTH_SHORT
) = context?.toast(message, duration)

fun Fragment.alert(
    message: Any,
    dialogTitle: Int? = null,
    okTitle: Int? = null,
    showCancel: Boolean = true,
    cancelTitle: Int? = null,
    cancelHandler: UnitHandler? = null,
    okHandler: UnitHandler? = null
) = context?.alert(message, dialogTitle, okTitle, showCancel, cancelTitle, cancelHandler, okHandler)

fun TextView.clear() {
    this.text = null
    this.setOnClickListener(null)
}

fun ImageView.clear(isOnlyImage: Boolean = false) {
    this.setImageResource(0)
    this.setImageBitmap(null)
    this.setImageDrawable(null)
    if (isOnlyImage) this.setOnClickListener(null)
}

fun Button.clear(isClearText: Boolean = true) {
    if (isClearText) this.text = null
    this.setOnClickListener(null)
}

fun CheckBox.clear() {
    this.setOnCheckedChangeListener(null)
}

fun Toolbar.clear() {
    this.title = null
    this.setNavigationOnClickListener(null)
}

fun AutoCompleteTextView.clear() {
    onFocusChangeListener = null
    setOnEditorActionListener(null)
    setOnClickListener(null)
    setAdapter(null)
}

fun ViewGroup.clear() {
    this.children
        .asSequence()
        .forEach { it.clearView() }
}

fun TabLayout.clear() {
    this.clearOnTabSelectedListeners()
    this.removeAllTabs()
}

fun EditText.clear() {
    setOnEditorActionListener(null)
    onFocusChangeListener = null
    setOnClickListener(null)
}

fun View.clearView() {
    when (this) {
        is ViewGroup -> this.clear()
        is ImageView -> this.clear()
        is Button -> this.clear()
        is AutoCompleteTextView -> this.clear()
        is EditText -> this.clear()
        is TextView -> this.clear()
        is CheckBox -> this.clear()
        is Toolbar -> this.clear()
        is TabLayout -> this.clear()
    }
}

fun View.hide(gone: Boolean = true) {
    visibility = if (gone) View.GONE else View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}