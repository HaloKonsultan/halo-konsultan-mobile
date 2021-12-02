package com.halokonsultan.mobile.data.model

data class Province(
        val province_id: Int,
        val province: String
){
    override fun toString(): String {
        return province
    }
}