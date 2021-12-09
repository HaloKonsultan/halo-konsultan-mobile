package com.halokonsultan.mobile.data.model

import android.os.Parcelable
import com.halokonsultan.mobile.base.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConsultationsDocument(
        override val id: Int,
        val description: String,
        val `file`: String?,
        val name: String
): BaseModel(id), Parcelable