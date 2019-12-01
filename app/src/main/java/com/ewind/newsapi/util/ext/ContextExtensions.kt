package com.ewind.newsapi.util.ext

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


/**
 * Checks network connectivity
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isNetworkStatusAvailable(): Boolean {
    val connectivityManager = this
        .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let {
        val netInfo = it.activeNetworkInfo
        netInfo?.let {
            if (netInfo.isConnected) return true
        }
    }
    return false
}

/**
 * Execute block of code if network is available
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
inline fun Context.withNetwork(available: () -> Unit, error: () -> Unit) {
    if (isNetworkStatusAvailable()) {
        available()
    } else {
        error()
    }
}

/**
 * Get color from resources
 */
fun Context.getCompatColor(@ColorRes colorInt: Int): Int =
    ContextCompat.getColor(this, colorInt)

/**
 * Get drawable from resources
 */
fun Context.getCompatDrawable(@DrawableRes drawableRes: Int): Drawable? =
    ContextCompat.getDrawable(this, drawableRes)

fun Context.getCompatColorState(@ColorRes colorInt: Int) =
    ContextCompat.getColorStateList(this, colorInt)

/**
 * Start Activity from context
 * */
inline fun <reified T : Activity> Context?.startActivity(func: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java).apply {
        func()
    }
    this?.startActivity(intent)
}

inline fun <reified T : Activity> Activity?.startActivityForResult(
    result: Int,
    func: Intent.() -> Unit
) {
    val intent = Intent(this, T::class.java).apply {
        func()
    }
    this?.startActivityForResult(intent, result)
}

inline fun <reified T : Activity> Fragment?.startActivityForResult(
    result: Int,
    func: Intent.() -> Unit
) {
    val intent = Intent(this?.context, T::class.java).apply {
        func()
    }
    this?.startActivityForResult(intent, result)
}


