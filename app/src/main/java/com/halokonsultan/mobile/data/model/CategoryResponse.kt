package com.halokonsultan.mobile.data.model

data class CategoryResponse(
    val code: String,
    val `data`: List<Category>,
    val message: String
)