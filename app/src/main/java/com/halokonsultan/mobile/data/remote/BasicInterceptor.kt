package com.halokonsultan.mobile.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class BasicInterceptor(private var token: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val basicRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(basicRequest)
    }

}