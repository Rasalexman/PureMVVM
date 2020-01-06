package com.rasalexman.core.common.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned
import com.rasalexman.core.BuildConfig
import com.rasalexman.core.common.typealiases.OutHandler
import timber.log.Timber

const val DEFAULT_TAG = "------> "

inline fun Any.log(lambda: () -> String?) {
    if (BuildConfig.DEBUG) {
        Timber.d("$DEFAULT_TAG${lambda().orEmpty()}")
    }
}

fun Any.log(message: String?, tag: String = DEFAULT_TAG) {
    if (BuildConfig.DEBUG) {
        Timber.d("$tag${message.orEmpty()}")
    }
}

fun <T> T?.doIfNull(input: OutHandler<T>): T {
    return this ?: input()
}

fun <T, R> T?.takeIfNull(input: OutHandler<R>): Any? {
    return this ?: input()
}

inline fun <reified I : Any> Any.applyForType(block: (I) -> Unit): Any  {
    if (this::class == I::class) block(this as I)
    return this
}

inline fun <T, R> T?.withBy(block: T.() -> R): R? {
    return this?.block()
}

inline fun <reified T, reified R> R.unsafeLazy(noinline init: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, init)

fun String.fromHTML(): Spanned {
    return if (Build.VERSION.SDK_INT < 24) Html.fromHtml(this)
    else Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
}