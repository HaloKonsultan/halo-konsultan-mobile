package com.halokonsultan.mobile.data.remote

import com.halokonsultan.mobile.data.model.City
import com.halokonsultan.mobile.data.model.Province
import com.halokonsultan.mobile.data.model.dto.CityResponse
import com.halokonsultan.mobile.data.model.dto.RajaOngkirResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {

    @GET("province")
    suspend fun getAllProvince(): Response<RajaOngkirResponse<Province>>

    @GET("city")
    suspend fun getAllCity(
        @Query("province") idProvince: Int
    ): Response<RajaOngkirResponse<City>>
}