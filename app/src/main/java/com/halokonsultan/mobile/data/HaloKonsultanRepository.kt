package com.halokonsultan.mobile.data

import androidx.room.withTransaction
import com.halokonsultan.mobile.data.local.HaloKonsultanDatabase
import com.halokonsultan.mobile.data.preferences.Preferences
import com.halokonsultan.mobile.data.remote.HaloKonsultanApi
import com.halokonsultan.mobile.data.remote.LocationApi
import com.halokonsultan.mobile.utils.networkBoundResource
import okhttp3.MultipartBody
import javax.inject.Inject

class HaloKonsultanRepository @Inject constructor(
        private val api: HaloKonsultanApi,
        private val locationApi: LocationApi,
        private val preferences: Preferences,
        private val db: HaloKonsultanDatabase
) {

    suspend fun login(email: String, password: String) = api.login(email, password)

    suspend fun register(name: String, email: String, password: String) = api.register(name, email, password)

    suspend fun logout() = api.logout()

    suspend fun getProfile(id: Int) = api.getProfile(id)

    suspend fun getAllProvince() = locationApi.getAllProvince()

    suspend fun getAllCity(id: Int) = locationApi.getAllCity(id)

    suspend fun getRandomCategories() = api.getRandomCategories()

    suspend fun getAllCategories() = api.getAllCategories()

    suspend fun getNearestConsultants(city: String) = api.getNearestConsultants(city)

    suspend fun getConsultantDetail(id: Int) = api.getConsultantDetail(id)

    suspend fun searchConsultantByName(name: String) = api.search(name)

    suspend fun getConsultantByCategory(id: Int) = api.getConsultantByCategory(id)

    suspend fun bookingConsultation(title: String,
                                    consultantId: Int,
                                    userId: Int,
                                    description: String,
                                    isOnline: Int,
                                    isOffline: Int,
                                    location: String) =
        api.bookingConsultation(title, consultantId, userId, description, isOnline, isOffline, location)

    suspend fun getListConsultation(userId: Int, status: String) =
        api.getConsultationList(userId, status)

    suspend fun getDetailConsultation(id: Int) = api.getDetailConsultation(id)

    suspend fun getPrefDate(id: Int, date: String, time: String) = api.getPrefDate(id, date, time)

    suspend fun uploadDocument(file: MultipartBody.Part, id: Int, documentId: Int) =
            api.uploadDocument(file, id, documentId)

    suspend fun pay(id:Int) = api.pay(id)

    // preference related function
    fun saveToken(token: String) = preferences.saveToken(token)
    fun saveUserId(id: Int) = preferences.saveUserId(id)
    fun setLoggedIn(value: Boolean) = preferences.isLoggedIn(value)
    fun getUserId() = preferences.userID
    fun isLoggedIn() = preferences.loggedIn
    fun setExpirationTime(value: Int) = preferences.setExpirationTime(value)
    fun getExpiredTime() = preferences.expiredTime
    fun setFirstTime(value: Boolean) = preferences.isFirstTime(value)
    fun isFirstTime() = preferences.firstTime

    // Experiment with networkBoundResource
    fun getRandomCategoriesAdvance() = networkBoundResource(
        query = {
            db.getDao().getRandomCategories()
        },
        fetch = {
            val response = api.getRandomCategories()
            response.body()?.data
        },
        saveFetchResult = { categories ->
            if (categories != null) {
                db.withTransaction {
                    db.getDao().deleteAllCategories()
                    db.getDao().upsertCategories(categories)
                }
            }
        }
    )

    fun getNearestConsultantAdvance(city: String) = networkBoundResource(
        query = {
            db.getDao().getNearestConsultants(city)
        },
        fetch = {
            val response = api.getNearestConsultants(city)
            response.body()?.data?.data
        },
        saveFetchResult = { consultants ->
            if (consultants != null) {
                db.withTransaction {
                    db.getDao().deleteConsultantsByCity(city)
                    db.getDao().upsertConsultants(consultants)
                }
            }
        }
    )

    fun getConsultantByCategoryAdvance(id: Int) = networkBoundResource(
            query = {
                db.getDao().getConsultantByCategory(id)
            },
            fetch = {
                val response = api.getConsultantByCategory(id)
                response.body()?.data?.data
            },
            saveFetchResult = { consultants ->
                if (consultants != null) {
                    db.withTransaction {
                        db.getDao().deleteConsultantsByCategory(id)
                        db.getDao().upsertConsultants(consultants)
                    }
                }
            }
    )

    fun getConsultationByStatusAdvance(id: Int, status: String) = networkBoundResource(
        query = {
            db.getDao().getConsultationsByStatus(id, status)
        },
        fetch = {
            val response = api.getConsultationList(id, status)
            response.body()?.data?.data
        },
        saveFetchResult = { consultations ->
            if (consultations != null) {
                db.withTransaction {
                    db.getDao().deleteConsultationsByStatus(status)
                    db.getDao().upsertConsultations(consultations)
                }
            }
        }
    )

    fun getProfileAdvance(id: Int) = networkBoundResource(
        query = {
            db.getDao().getProfile(id)
        },
        fetch = {
            val response = api.getProfile(id)
            response.body()?.data
        },
        saveFetchResult = { profile ->
            if (profile != null) {
                db.withTransaction {
                    db.getDao().deleteProfile(id)
                    db.getDao().upsertProfile(profile)
                }
            }
        }
    )
}