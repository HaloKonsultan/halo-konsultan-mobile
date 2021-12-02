package com.halokonsultan.mobile.data.model

data class City(
        val city_id: Int,
        val province_id: String,
        val city_name: String,
        val postal_code: String,
        val province: String,
        val type: String
) {
    override fun toString(): String {
        return "$type $city_name"
    }
}