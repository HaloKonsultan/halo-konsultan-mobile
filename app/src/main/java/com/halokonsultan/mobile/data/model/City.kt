package com.halokonsultan.mobile.data.model

data class City(
    val id: String,
    val province_id: String,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}