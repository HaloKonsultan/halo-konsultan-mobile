package com.halokonsultan.mobile.utils

import android.app.Activity
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.halokonsultan.mobile.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    fun String.isValidEmail() =
            !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun formatPrice(data: Int): String {
        val numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.maximumFractionDigits = 0
        return numberFormat.format(data)
    }

    fun setTitleTextView(activity: Activity, title: String) {
        activity.findViewById<TextView>(R.id.titleTextView).text = title
    }

    fun setStatusBarLightText(window: Window, isLight: Boolean) {
        setStatusBarLightTextOldApi(window, isLight)
        setStatusBarLightTextNewApi(window, isLight)
    }


    fun setStatusBarLightTextOldApi(window: Window, isLight: Boolean) {
        val decorView = window.decorView
        decorView.systemUiVisibility =
                if (isLight) {
                    decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                } else {
                    decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
    }

    fun setStatusBarLightTextNewApi(window: Window, isLightText: Boolean) {
        ViewCompat.getWindowInsetsController(window.decorView)?.apply {
            isAppearanceLightStatusBars = !isLightText
        }
    }
}