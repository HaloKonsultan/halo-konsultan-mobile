package com.halokonsultan.mobile.data.model.dto

import com.halokonsultan.mobile.data.model.DetailConsultation

data class DetailConsultationResponse(
    val code: Int,
    val `data`: DetailConsultation,
    val message: String
)