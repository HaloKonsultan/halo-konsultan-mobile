package com.halokonsultan.mobile.data.model.dto

import com.halokonsultan.mobile.data.model.Profile

data class AuthResponse(
        val access_token: String = "",
        val token_type: String,
        val expires_in: Int,
        val `data`: Profile
)
