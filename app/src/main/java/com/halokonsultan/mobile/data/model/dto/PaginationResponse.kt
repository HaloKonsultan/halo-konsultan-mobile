package com.halokonsultan.mobile.data.model.dto

data class PaginationResponse<T>(
        val code: Int,
        val `data`: PaginationData<T>
)
