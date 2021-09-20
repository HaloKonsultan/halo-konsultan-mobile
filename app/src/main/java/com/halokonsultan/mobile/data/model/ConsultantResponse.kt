package com.halokonsultan.mobile.data.model

data class ConsultantResponse(
        val code: String,
        val `data`: List<Consultant>,
        val message: String
)