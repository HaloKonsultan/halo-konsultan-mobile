package com.halokonsultan.mobile.utils

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import com.halokonsultan.mobile.BuildConfig
import com.halokonsultan.mobile.R
import java.io.File
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

    fun Boolean.toInt() = if (this) 1 else 0

    fun Int.toBoolean() = this == 1

    fun String.trim(maxLength: Int) =
        if (this.length > maxLength)
            "${this.take(maxLength - 3)}..."
        else
            this

    fun formatPrice(data: Int): String {
        val numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.maximumFractionDigits = 0
        numberFormat.currency = Currency.getInstance("IDR")
        return numberFormat.format(data)
    }

    fun setTitleTextView(activity: Activity, title: String) {
        activity.findViewById<TextView>(R.id.titleTextView).text = title
    }

    fun setStatusBarLightText(window: Window, isLight: Boolean) {
        setStatusBarLightTextOldApi(window, isLight)
        setStatusBarLightTextNewApi(window, isLight)
    }


    private fun setStatusBarLightTextOldApi(window: Window, isLight: Boolean) {
        val decorView = window.decorView
        decorView.systemUiVisibility =
                if (isLight) {
                    decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                } else {
                    decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
    }

    private fun setStatusBarLightTextNewApi(window: Window, isLightText: Boolean) {
        ViewCompat.getWindowInsetsController(window.decorView)?.apply {
            isAppearanceLightStatusBars = !isLightText
        }
    }

    fun getPageNumberFromUrl(url: String): Int {
        val temp = url.split("=")
        return temp[1].toInt()
    }

    fun addRootDomainIfNeeded(url: String) =
        if (url.contains("images/")) ROOT_DOMAIN + url
        else url

    fun Activity.openFile(filename: String?) {
        val file =  File(filesDir, filename)

        if(file.exists() && filename != null) {
            val type = if (filename.contains(".doc") || filename.contains(".docx")) {
                "application/msword"
            } else if (filename.contains(".pdf")) {
                "application/pdf"
            } else if (filename.contains(".ppt") || filename.contains(".pptx")) {
                "application/vnd.ms-powerpoint"
            } else if (filename.contains(".xls") || filename.contains(".xlsx")) {
                "application/vnd.ms-excel"
            } else if (filename.contains(".zip") || filename.contains(".rar")) {
                "application/x-wav"
            } else if (filename.contains(".rtf")) {
                "application/rtf"
            } else if (filename.contains(".wav") || filename.toString().contains(".mp3")) {
                "audio/x-wav"
            } else if (filename.contains(".gif")) {
                "image/gif"
            } else if (filename.contains(".jpg") || filename.contains(".jpeg") || filename.contains(".png")) {
                "image/jpeg"
            } else if (filename.contains(".txt")) {
                "text/plain"
            } else if (filename.contains(".3gp") || filename.contains(".mpg") ||
                filename.contains(".mpeg") || filename.contains(".mpe") ||
                filename.contains(".mp4") || filename.contains(".avi")
            ) {
                "video/*"
            } else {
                "*/*"
            }

            Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(FileProvider.getUriForFile(this@openFile,
                    BuildConfig.APPLICATION_ID + ".provider", file), type)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                try {
                    startActivity(this)
                } catch (e: Exception) {
                    Log.d("coba", "openPdf: ${e.message}")
                }
            }
        } else {
            Log.d("coba", "openPdf: file not exist")
        }
    }
}