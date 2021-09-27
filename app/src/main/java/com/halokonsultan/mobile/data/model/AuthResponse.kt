package com.halokonsultan.mobile.data.model

data class AuthResponse(
        val code: String,
        val `data`: Profile,
        val message: String
)
