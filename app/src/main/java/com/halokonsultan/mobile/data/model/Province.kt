package com.halokonsultan.mobile.data.model

data class Province(
    val id: String,
    val name: String
){
    override fun toString(): String {
        return name
    }
}