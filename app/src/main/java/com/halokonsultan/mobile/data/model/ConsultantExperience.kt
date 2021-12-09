package com.halokonsultan.mobile.data.model

import com.halokonsultan.mobile.base.BaseModel

data class ConsultantExperience(
        override val id: Int,
        val position: String,
        val start_year: String,
        val end_year: String
): BaseModel(id)