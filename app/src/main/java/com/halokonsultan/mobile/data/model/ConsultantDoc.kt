package com.halokonsultan.mobile.data.model

import com.halokonsultan.mobile.base.BaseModel

data class ConsultantDoc(
        override val id: Int,
        val photo: String
): BaseModel(id)