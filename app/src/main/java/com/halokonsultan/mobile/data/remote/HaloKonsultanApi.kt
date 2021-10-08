package com.halokonsultan.mobile.data.remote

import com.halokonsultan.mobile.data.model.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

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

    @GET("consultant/{id}")
    suspend fun getConsultantDetail(
        @Path("id") id: Int
    ): Response<DetailConsultantResponse>

    @POST("consultant")
    suspend fun search(
        @Query("q") keyword: String
    ): Response<ConsultantResponse>

    @GET("consultant/category/{category_id}")
    suspend fun getConsultantByCategory(
        @Path("category_id") categoryId: Int
    ): Response<ConsultantResponse>

    @POST("consultations")
    suspend fun bookingConsultation(
        @Body consultationInfo: HashMap<String, Any>
    ): Response<DetailConsultationResponse>

    @GET("consultaions/user/{user_id}/status/{status}")
    suspend fun getConsultationList(
        @Path("user_id") userId: Int,
        @Path("status") status: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<ConsultationResponse>

    @GET("consultation/{id}")
    suspend fun getDetailConsultation(
        @Path("id") id: Int
    ): Response<DetailConsultationResponse>

    @PATCH("consultations/{id}")
    suspend fun getPrefDate(
        @Path("id") id: Int,
        @Body prefDate: HashMap<String, Any>
    ): Response<DetailConsultationResponse>

    @Multipart
    @POST("/consultations/{id}/upload-document/{id_document}")
    suspend fun uploadDocument(
        @Part file: MultipartBody.Part,
        @Path("id") id: Int,
        @Path("id_document") documentId: Int
    ): Response<DetailConsultationResponse>

    @Multipart
    @POST("/consultations/{id}/change-document/{id_document}")
    suspend fun editDocument(
            @Part file: MultipartBody.Part,
            @Path("id") id: Int,
            @Path("id_document") documentId: Int
    ): Response<DetailConsultationResponse>
}