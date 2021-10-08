package com.halokonsultan.mobile.data

import com.halokonsultan.mobile.data.remote.HaloKonsultanApi
import okhttp3.MultipartBody
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

    suspend fun getConsultantDetail(id: Int) = api.getConsultantDetail(id)

    suspend fun searchConsultantByName(name: String) = api.search(name)

    suspend fun getConsultantByCategory(id: Int) = api.getConsultantByCategory(id)

    suspend fun bookingConsultation(title: String, description: String, isOnline: Boolean, isOffline: Boolean, location: String) =
        api.bookingConsultation(hashMapOf(
            "title" to title,
            "description" to description,
            "is_online" to isOnline,
            "is_offline" to isOffline,
            "location" to location
        ))

    suspend fun getListConsultation(userId: Int, status: String, limit: Int, page: Int) =
        api.getConsultationList(userId, status, limit, page)

    suspend fun getDetailConsultation(id: Int) = api.getDetailConsultation(id)

    suspend fun getPrefDate(id: Int, date: String) = api.getPrefDate(id, hashMapOf(
        "date" to date
    ))

    suspend fun uploadDocument(file: MultipartBody.Part, id: Int, documentId: Int) =
            api.uploadDocument(file, id, documentId)

    suspend fun editDocument(file: MultipartBody.Part, id: Int, documentId: Int) =
            api.editDocument(file, id, documentId)
}