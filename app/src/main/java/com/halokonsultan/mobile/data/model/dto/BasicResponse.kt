package com.halokonsultan.mobile.data.model.dto

data class BasicResponse<T>(
        val code: String,
        val data: T? = null,
        val message: String? = null
)
