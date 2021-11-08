package com.halokonsultan.mobile.data.model

data class Province(
    val id: Int,
    val nama: String
){
    override fun toString(): String {
        return nama
    }
}