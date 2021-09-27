package com.halokonsultan.mobile.data.remote

import com.halokonsultan.mobile.data.model.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface HaloKonsultanApi {

    @POST("register")
    suspend fun login(
            @Body authInfo: HashMap<String, Any>
    ): Response<AuthResponse>
}