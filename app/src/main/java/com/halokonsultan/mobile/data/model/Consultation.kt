package com.halokonsultan.mobile.data.model

data class Consultation(
    val id: Int,
    val consultant_id: Int,
    val name: String,
    val title: String,
    val status: String,
    val is_confirmed: Int,
    val date: String
)