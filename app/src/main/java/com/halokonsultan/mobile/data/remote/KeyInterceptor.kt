package com.halokonsultan.mobile.data.remote

import com.halokonsultan.mobile.utils.RAJAONGKIR_API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class KeyInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val basicRequest = request.newBuilder()
            .addHeader("key", RAJAONGKIR_API_KEY)
            .build()
        return chain.proceed(basicRequest)
    }
}