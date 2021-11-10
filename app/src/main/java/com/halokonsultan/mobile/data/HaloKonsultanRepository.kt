package com.halokonsultan.mobile.data

import android.util.Log
import androidx.room.withTransaction
import com.halokonsultan.mobile.data.local.HaloKonsultanDatabase
import com.halokonsultan.mobile.data.preferences.Preferences
import com.halokonsultan.mobile.data.remote.HaloKonsultanApi
import com.halokonsultan.mobile.data.remote.LocationApi
import com.halokonsultan.mobile.utils.GlobalState
import com.halokonsultan.mobile.utils.Utils
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

    suspend fun getAllProvince() = locationApi.getAllProvince()

    suspend fun getAllCity(id: Int) = locationApi.getAllCity(id)

    suspend fun getAllCategories() = api.getAllCategories()

    suspend fun getConsultantDetail(id: Int) = api.getConsultantDetail(id)

    suspend fun searchConsultantByName(name: String, page: Int) = api.search(name, page)

    suspend fun bookingConsultation(title: String,
                                    consultantId: Int,
                                    userId: Int,
                                    description: String,
                                    isOnline: Int,
                                    isOffline: Int,
                                    location: String) =
        api.bookingConsultation(title, consultantId, userId, description, isOnline, isOffline, location)

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

    fun getNearestConsultantAdvance(city: String, page: Int) = networkBoundResource(
        query = {
            db.getDao().getNearestConsultants(city)
        },
        fetch = {
            val response = api.getNearestConsultants(city, page)
            GlobalState.nextPageConsultant =
                    if (response.body()?.data?.next_page_url != null)
                        Utils.getPageNumberFromUrl(response.body()?.data?.next_page_url!!)
                    else
                        null
            response.body()?.data?.data
        },
        saveFetchResult = { consultants ->
            if (consultants != null) {
                db.withTransaction {
                    if (page == 1) db.getDao().deleteConsultantsByCity(city)
                    db.getDao().upsertConsultants(consultants)
                }
            }
        }
    )

    fun getConsultantByCategoryAdvance(id: Int, page: Int) = networkBoundResource(
            query = {
                db.getDao().getConsultantByCategory(id)
            },
            fetch = {
                val response = api.getConsultantByCategory(id, page)
                GlobalState.nextPageConsultant =
                        if (response.body()?.data?.next_page_url != null)
                            Utils.getPageNumberFromUrl(response.body()?.data?.next_page_url!!)
                        else
                            null
                Log.d("coba", "getConsultantByCategoryAdvance: ${GlobalState.nextPageConsultant}")
                response.body()?.data?.data
            },
            saveFetchResult = { consultants ->
                if (consultants != null) {
                    db.withTransaction {
                        if (page == 1) db.getDao().deleteConsultantsByCategory(id)
                        db.getDao().upsertConsultants(consultants)
                    }
                }
            }
    )

    fun getConsultationByStatusAdvance(id: Int, status: String, page: Int) = networkBoundResource(
        query = {
            db.getDao().getConsultationsByStatus(id, status)
        },
        fetch = {
            val response = api.getConsultationList(id, status, page)
            GlobalState.nextPageConsultation =
                    if (response.body()?.data?.next_page_url != null)
                        Utils.getPageNumberFromUrl(response.body()?.data?.next_page_url!!)
                    else
                        null
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