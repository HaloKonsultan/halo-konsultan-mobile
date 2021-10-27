package com.halokonsultan.mobile.data.model

data class Profile(
        val id: Int,
        val name: String,
        val email: String,
        val location: String,
        val photo: String,
        val firebase_id: String
)