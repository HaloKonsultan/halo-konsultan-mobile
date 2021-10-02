package com.halokonsultan.mobile.data

import com.halokonsultan.mobile.data.remote.HaloKonsultanApi
import javax.inject.Inject

class HaloKonsultanRepository @Inject constructor(
        private val api: HaloKonsultanApi
) {

    suspend fun login(email: String, password: String) = api.login(hashMapOf(
            "email" to email,
            "password" to password
    ))

    suspend fun register(name: String, email: String, password: String) = api.login(hashMapOf(
            "name" to name,
            "email" to email,
            "password" to password
    ))

    suspend fun getRandomCategories() = api.getRandomCategories()

    suspend fun getNearestConsultants(city: String) = api.getNearestConsultants(city)

}