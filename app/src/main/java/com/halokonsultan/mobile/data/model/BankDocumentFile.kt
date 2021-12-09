package com.halokonsultan.mobile.data.model

import androidx.documentfile.provider.DocumentFile
import com.halokonsultan.mobile.base.BaseModel

data class BankDocumentFile(
    val name: String,
    val type: String,
    val file: DocumentFile
): BaseModel(name.hashCode())
