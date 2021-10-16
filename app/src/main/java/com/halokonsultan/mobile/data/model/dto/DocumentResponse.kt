package com.halokonsultan.mobile.data.model.dto

import com.halokonsultan.mobile.data.model.ConsultationsDocument

data class DocumentResponse(
        val code: String,
        val `data`: ConsultationsDocument,
        val message: String
)
