package com.halokonsultan.mobile.data.model

import com.halokonsultan.mobile.base.BaseModel

data class ParentCategory(
    override val id: Int,
    val name: String,
    val child: List<Category>
): BaseModel(id)
