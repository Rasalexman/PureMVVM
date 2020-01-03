package com.rasalexman.core.common.extensions

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.mincor.kodi.core.applyIf
import com.rasalexman.core.R
import com.rasalexman.core.common.typealiases.UnitHandler

const val INTERNET_SETTINGS_INTENT_CODE = 9023
private var alertDialog: Dialog? = null
fun Context.toast(message: Any, duration: Int = Toast.LENGTH_SHORT) {
    when (message) {
        is String -> {
            if (message.isNotEmpty())
                Toast.makeText(this, message, duration).show()
        }
        is Int -> Toast.makeText(this, message, duration).show()
        else -> Unit
    }
}

fun Activity.openNetworkSettings() {
    val intent = Intent(Settings.ACTION_SETTINGS)
    intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting")
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivityForResult(intent, INTERNET_SETTINGS_INTENT_CODE)
}

fun Context.closeAlert(dismiss: Boolean = true) {
    if(dismiss) alertDialog?.dismiss()
    alertDialog = null
}

fun Context.alert(
    message: Any,
    dialogTitle: Int? = null,
    okTitle: Int? = null,
    showCancel: Boolean = true,
    cancelTitle: Int? = null,
    cancelHandler: UnitHandler? = null,
    okHandler: UnitHandler? = null
) {
    closeAlert()
    alertDialog = AlertDialog
        .Builder(this)
        .setTitle(dialogTitle ?: R.string.title_warning)
        .setCancelable(false)
        .setPositiveButton(okTitle ?: R.string.title_try_again) { dialogInterface, _ ->
            dialogInterface.dismiss()
            closeAlert(dismiss = false)
            okHandler?.invoke()
        }.applyIf(showCancel) {
            it.setNegativeButton(cancelTitle ?: R.string.title_cancel) { dialogInterface, _ ->
                dialogInterface.dismiss()
                closeAlert(dismiss = false)
                cancelHandler?.invoke()
            }
        }.run {
            when (message) {
                is String -> setMessage(message)
                is Int -> setMessage(message)
                else -> setMessage(message.toString())
            }
            show()
        }
}

///----- DIMENSIONS
//returns dip(dp) dimension value in pixels
fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()
fun Context.dip(value: Float): Int = (value * resources.displayMetrics.density).toInt()

//return sp dimension value in pixels
fun Context.sp(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()
fun Context.sp(value: Float): Int = (value * resources.displayMetrics.scaledDensity).toInt()

//converts px value into dip or sp
fun Context.px2dip(px: Int): Float = px.toFloat() / resources.displayMetrics.density
fun Context.px2sp(px: Int): Float = px.toFloat() / resources.displayMetrics.scaledDensity

fun Context.dimen(@DimenRes resource: Int): Int = resources.getDimensionPixelSize(resource)

fun Context.color(@ColorRes resource: Int): Int = ContextCompat.getColor(this, resource)
fun Context.string(@StringRes stringRes: Int): String = this.getString(stringRes)
fun Context.drawable(@DrawableRes drawableResId: Int): Drawable? = ContextCompat.getDrawable(this, drawableResId)
fun Context.drawableWithParams(@DrawableRes drawableResId: Int, paramsInitializer: (Drawable?) -> Unit): Drawable? {
    val itemDrawable: Drawable? = ContextCompat.getDrawable(this, drawableResId)
    paramsInitializer(itemDrawable)
    return itemDrawable
}