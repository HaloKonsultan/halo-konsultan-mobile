package com.halokonsultan.mobile.data.remote

import com.halokonsultan.mobile.data.model.City
import com.halokonsultan.mobile.data.model.Province
import com.halokonsultan.mobile.data.model.dto.LocationResponse
import com.halokonsultan.mobile.utils.BINDERBYTE_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {

    @GET("provinsi")
    suspend fun getAllProvince(
        @Query("api_key") key: String = BINDERBYTE_API_KEY
    ): Response<LocationResponse<Province>>

    @GET("kabupaten")
    suspend fun getAllCity(
        @Query("api_key") key: String = BINDERBYTE_API_KEY,
        @Query("id_provinsi") idProvince: String
    ): Response<LocationResponse<City>>
}