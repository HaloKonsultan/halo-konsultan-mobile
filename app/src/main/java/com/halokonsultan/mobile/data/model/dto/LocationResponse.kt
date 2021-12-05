package com.halokonsultan.mobile.data.model.dto

data class LocationResponse<T>(
    val code: Int,
    val message: String,
    val value: List<T>
)