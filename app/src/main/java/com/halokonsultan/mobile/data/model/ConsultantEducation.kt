package com.halokonsultan.mobile.data.model

data class ConsultantEducation(
        val id: Int,
        val consultant_id: Int,
        val institution_name: String,
        val major: String,
        val start_year: String,
        val end_year: String,
)