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

    @GET("categories/random")
    suspend fun getRandomCategories(): Response<CategoryResponse>

    @GET("categories/all")
    suspend fun getAllCategories(): Response<ParentCategoryResponse>

    // Consultant

    @GET("consultants/city/{city}")
    suspend fun getNearestConsultants(
        @Path("city") city: String
    ): Response<ConsultantPaginationResponse>

    @GET("consultants/{id}")
    suspend fun getConsultantDetail(
        @Path("id") id: Int
    ): Response<DetailConsultantResponse>

    @GET("consultants/search/{name}")
    suspend fun search(
        @Path("name") keyword: String
    ): Response<ConsultantPaginationResponse>

    @GET("consultants/category/{category_id}")
    suspend fun getConsultantByCategory(
        @Path("category_id") categoryId: Int
    ): Response<ConsultantPaginationResponse>

    // Consultation

    @FormUrlEncoded
    @POST("consultations")
    suspend fun bookingConsultation(
        @Field("title") title: String,
        @Field("consultant") consultantId: Int,
        @Field("user") userId: Int,
        @Field("description") description: String,
        @Field("is_online") isOnline: Int,
        @Field("is_offline") isOffline: Int,
        @Field("location") location: String
    ): Response<DetailConsultationResponse>

    @GET("consultations/user/{user_id}/status/{status}")
    suspend fun getConsultationList(
        @Path("user_id") userId: Int,
        @Path("status") status: String
    ): Response<ConsultationPaginationResponse>

    @GET("consultations/{id}")
    suspend fun getDetailConsultation(
        @Path("id") id: Int
    ): Response<DetailConsultationResponse>

    @FormUrlEncoded
    @PATCH("consultations/{id}")
    suspend fun getPrefDate(
        @Path("id") id: Int,
        @Field("date") date: String,
        @Field("time") time: String
    ): Response<DetailConsultationResponse>

    @Multipart
    @POST("consultations/{id}/upload-document/{id_document}")
    suspend fun uploadDocument(
        @Part file: MultipartBody.Part,
        @Path("id") id: Int,
        @Path("id_document") documentId: Int
    ): Response<DetailConsultationResponse>
}