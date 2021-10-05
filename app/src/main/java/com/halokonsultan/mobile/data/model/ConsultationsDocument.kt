package com.halokonsultan.mobile.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConsultationsDocument(
    val description: String,
    val `file`: String,
    val name: String
): Parcelable