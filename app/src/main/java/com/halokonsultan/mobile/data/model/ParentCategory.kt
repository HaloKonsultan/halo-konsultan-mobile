package com.halokonsultan.mobile.data.model

data class ParentCategory(
    val id: Int,
    val name: String,
    val child: List<Category>
)
