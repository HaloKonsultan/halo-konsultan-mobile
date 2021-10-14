package com.halokonsultan.mobile.data.model.dto

import com.halokonsultan.mobile.data.model.DetailConsultant

data class DetailConsultantResponse(
    val code: Int,
    val `data`: DetailConsultant,
    val message: String
)