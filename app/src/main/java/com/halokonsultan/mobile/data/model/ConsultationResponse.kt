package com.halokonsultan.mobile.data.model

data class ConsultationResponse(
    val code: Int,
    val `data`: List<Consultation>,
    val message: String
)
