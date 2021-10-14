package com.halokonsultan.mobile.data.model.dto

import com.halokonsultan.mobile.data.model.Category

data class CategoryResponse(
    val code: String,
    val `data`: List<Category>,
    val message: String
)