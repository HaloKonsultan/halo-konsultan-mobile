package com.halokonsultan.mobile.data.remote

import com.halokonsultan.mobile.data.model.dto.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface HaloKonsultanApi {

    // Auth

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
            @Field(value = "email", encoded = true) email: String,
            @Field("password") password: String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
            @Field("name") name: String,
            @Field("email") email: String,
            @Field("password") password: String
    ): Response<AuthResponse>

    @POST("logout")
    suspend fun logout(): Response<LogoutResponse>

    // Category

    @GET("category/random")
    suspend fun getRandomCategories(): Response<CategoryResponse>

    @GET("category/all")
    suspend fun getAllCategories(): Response<ParentCategoryResponse>

    // Consultant

    @GET("category/consultants/{city}")
    suspend fun getNearestConsultants(
        @Path("city") city: String
    ): Response<ConsultantPaginationResponse>

    @GET("consultant/{id}")
    suspend fun getConsultantDetail(
        @Path("id") id: Int
    ): Response<DetailConsultantResponse>

    @GET("consultant/search/{name}")
    suspend fun search(
        @Path("name") keyword: String
    ): Response<ConsultantPaginationResponse>

    @GET("consultant/category/{category_id}")
    suspend fun getConsultantByCategory(
        @Path("category_id") categoryId: Int
    ): Response<ConsultantPaginationResponse>

    // Consultation

    @POST("consultations")
    suspend fun bookingConsultation(
        @Body consultationInfo: HashMap<String, Any>
    ): Response<DetailConsultationResponse>

    @GET("consultation/user/{user_id}/status/{status}")
    suspend fun getConsultationList(
        @Path("user_id") userId: Int,
        @Path("status") status: String
    ): Response<ConsultationPaginationResponse>

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