package com.halokonsultan.mobile.data.model

import androidx.documentfile.provider.DocumentFile

data class BankDocumentFile(
    val name: String,
    val type: String,
    val file: DocumentFile
)
