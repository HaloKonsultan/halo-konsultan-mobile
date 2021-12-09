package com.halokonsultan.mobile.data.model

import com.halokonsultan.mobile.base.BaseModel

data class ConsultantEducation(
        override val id: Int,
        val institution_name: String,
        val major: String,
        val start_year: String,
        val end_year: String,
): BaseModel(id)