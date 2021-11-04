package com.halokonsultan.mobile.data.remote

import com.halokonsultan.mobile.data.model.dto.CityResponse
import com.halokonsultan.mobile.data.model.dto.ProvinceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {

    @GET("provinsi")
    suspend fun getAllProvince(): Response<ProvinceResponse>

    @GET("kota")
    suspend fun getAllCity(
        @Query("id_provinsi") idProvince: Int
    ): Response<CityResponse>

}