package com.halokonsultan.mobile.data.remote

import com.halokonsultan.mobile.data.model.*
import com.halokonsultan.mobile.data.model.dto.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface BaseApi {

    // Auth

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
            @Field(value = "email", encoded = true) email: String,
            @Field("password") password: String,
            @Field("device_token") token: String
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

    // Profile

    @GET("profile/{id}")
    suspend fun getProfile(
        @Path("id") id: Int
    ): Response<BasicResponse<Profile>>

    @FormUrlEncoded
    @PATCH("update/{id}")
    suspend fun updateProfile(
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("province") province: String,
        @Field("city") city: String
    ): Response<BasicResponse<Profile>>

    // Category

    @GET("categories/random")
    suspend fun getRandomCategories(): Response<BasicResponse<List<Category>>>

    @GET("categories/all")
    suspend fun getAllCategories(): Response<BasicResponse<List<ParentCategory>>>

    // Consultant

    @GET("consultants/city/{city}")
    suspend fun getNearestConsultants(
        @Path("city") city: String,
        @Query("page") page: Int
    ): Response<PaginationResponse<Consultant>>

    @GET("consultants/{id}")
    suspend fun getConsultantDetail(
        @Path("id") id: Int
    ): Response<BasicResponse<DetailConsultant>>

    @GET("consultants/search/{name}")
    suspend fun search(
        @Path("name") keyword: String,
        @Query("page") page: Int
    ): Response<PaginationResponse<Consultant>>

    @GET("consultants/category/{category_id}")
    suspend fun getConsultantByCategory(
        @Path("category_id") categoryId: Int,
        @Query("page") page: Int
    ): Response<PaginationResponse<Consultant>>

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
    ): Response<BasicResponse<DetailConsultation>>

    @GET("consultations/user/{user_id}/status/{status}")
    suspend fun getConsultationList(
        @Path("user_id") userId: Int,
        @Path("status") status: String,
        @Query("page") page: Int
    ): Response<PaginationResponse<Consultation>>

    @GET("consultations/{id}")
    suspend fun getDetailConsultation(
        @Path("id") id: Int
    ): Response<BasicResponse<DetailConsultation>>

    @FormUrlEncoded
    @PATCH("consultations/{id}")
    suspend fun getPrefDate(
        @Path("id") id: Int,
        @Field("date") date: String,
        @Field("time") time: String
    ): Response<BasicResponse<DetailConsultation>>

    @Multipart
    @POST("consultations/{id}/upload-document/{id_document}")
    suspend fun uploadDocument(
        @Part file: MultipartBody.Part,
        @Path("id") id: Int,
        @Path("id_document") documentId: Int
    ): Response<BasicResponse<DetailConsultation>>

    @FormUrlEncoded
    @POST("consultations/review/{id}")
    suspend fun reviewConsultation(
        @Path("id") id: Int,
        @Field("is_like") like: Int
    ): Response<BasicResponse<Consultant>>

    @FormUrlEncoded
    @POST("transaction/{id}/pay")
    suspend fun pay(
            @Path("id") idConsultation: Int,
            @Field("amount") amount: Int
    ): Response<BasicResponse<Transaction>>

    @GET("transaction/{id}")
    suspend fun getTransaction(
            @Path("id") id: Int
    ): Response<BasicResponse<Transaction>>

    // Forum

    @GET("forums/get-all-conversation/{id}")
    suspend fun getAllConversation(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): Response<PaginationResponse<Chat>>

    @GET("forums/get-all-messages/{id}")
    suspend fun getAllMessage(
        @Path("id") id: Int
    ): Response<BasicResponse<List<Message>>>

    @FormUrlEncoded
    @POST("forums/open-conversation")
    suspend fun openConversation(
        @Field("user_id") userId: Int,
        @Field("consultant_id") consultantId: Int
    ): Response<BasicResponse<Chat>>

    @FormUrlEncoded
    @POST("forums/send/{id}")
    suspend fun sendMessage(
        @Path("id") id: Int,
        @Field("user_id") userId: Int,
        @Field("message") message: String
    ): Response<BasicResponse<Message>>

    @PATCH("forums/read/{id}")
    suspend fun readMessage(
        @Path("id") messageId: Int
    ): Response<BasicResponse<Message>>

    // Other

    @FormUrlEncoded
    @POST("notification/{id}")
    suspend fun sendNotification(
        @Path("id") idConsultant: Int,
        @Field("title") title: String,
        @Field("message") body: String
    )
}