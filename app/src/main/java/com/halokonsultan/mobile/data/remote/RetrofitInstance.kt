package com.halokonsultan.mobile.data.remote

import com.halokonsultan.mobile.data.preferences.Preferences
import com.halokonsultan.mobile.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {

    fun createApi(): HaloKonsultanApi {
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttp = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(Preferences.instance.token))
            .addInterceptor(logger)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .build()

        return retrofit.create(HaloKonsultanApi::class.java)
    }
}