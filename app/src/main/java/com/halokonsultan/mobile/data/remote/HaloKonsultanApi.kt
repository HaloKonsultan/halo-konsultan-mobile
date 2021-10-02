package com.halokonsultan.mobile.data.remote

import com.halokonsultan.mobile.data.model.AuthResponse
import com.halokonsultan.mobile.data.model.CategoryResponse
import com.halokonsultan.mobile.data.model.ConsultantResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HaloKonsultanApi {

    @POST("login")
    suspend fun login(
            @Body authInfo: HashMap<String, Any>
    ): Response<AuthResponse>

    @POST("register")
    suspend fun register(
            @Body authInfo: HashMap<String, Any>
    ): Response<AuthResponse>

    @GET("category/random")
    suspend fun getRandomCategories(): Response<CategoryResponse>

    @GET("consultant/city/{city}")
    suspend fun getNearestConsultants(
        @Path("city") city: String
    ): Response<ConsultantResponse>
}