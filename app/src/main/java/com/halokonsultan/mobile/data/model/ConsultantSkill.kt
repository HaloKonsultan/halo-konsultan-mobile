package com.halokonsultan.mobile.data.model

import com.halokonsultan.mobile.base.BaseModel

data class ConsultantSkill(
        override val id: Int,
        val consultant_id: Int,
        val skills: String
): BaseModel(id)