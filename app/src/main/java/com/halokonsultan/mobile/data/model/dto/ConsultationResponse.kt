package com.halokonsultan.mobile.data.model.dto

import com.halokonsultan.mobile.data.model.Consultation

data class ConsultationResponse(
        val current_page: Int,
        val `data`: List<Consultation>,
        val first_page_url: String,
        val from: Int,
        val last_page: Int,
        val last_page_url: String,
        val next_page_url: Any,
        val path: String,
        val per_page: Int,
        val prev_page_url: Any,
        val to: Int,
        val total: Int
)
