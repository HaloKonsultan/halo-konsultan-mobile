package com.halokonsultan.mobile.data.model.dto

data class PaginationData<T>(
        val current_page: Int,
        val `data`: List<T>,
        val first_page_url: String,
        val from: Int,
        val last_page: Int,
        val last_page_url: String?,
        val next_page_url: String?,
        val path: String,
        val per_page: Int,
        val prev_page_url: String?,
        val to: Int,
        val total: Int
)
