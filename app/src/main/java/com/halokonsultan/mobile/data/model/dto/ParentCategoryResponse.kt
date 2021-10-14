package com.halokonsultan.mobile.data.model.dto

import com.halokonsultan.mobile.data.model.ParentCategory

data class ParentCategoryResponse(
    val code: String,
    val `data`: List<ParentCategory>,
    val message: String
)