package com.halokonsultan.mobile.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.documentfile.provider.DocumentFile
import com.halokonsultan.mobile.R
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
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

    fun booleanToInt(value: Boolean) = if (value) 1 else 0

    fun String.isValidEmail() =
            !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

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

    fun getPageNumberFromUrl(url: String): Int {
        val temp = url.split("=")
        return temp[1].toInt()
    }

    fun openFile(context: Context, url: DocumentFile) {
        val uri: Uri = url.uri
        val intent = Intent(Intent.ACTION_VIEW)
        if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword")
        } else if (url.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf")
        } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint")
        } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel")
        } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-wav")
        } else if (url.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf")
        } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav")
        } else if (url.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif")
        } else if (url.toString().contains(".jpg") || url.toString()
                .contains(".jpeg") || url.toString().contains(".png")
        ) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg")
        } else if (url.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain")
        } else if (url.toString().contains(".3gp") || url.toString()
                .contains(".mpg") || url.toString().contains(".mpeg") || url.toString()
                .contains(".mpe") || url.toString().contains(".mp4") || url.toString()
                .contains(".avi")
        ) {
            // Video files
            intent.setDataAndType(uri, "video/*")
        } else {
            //if you want you can also define the intent type for any other file
            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(uri, "*/*")
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}