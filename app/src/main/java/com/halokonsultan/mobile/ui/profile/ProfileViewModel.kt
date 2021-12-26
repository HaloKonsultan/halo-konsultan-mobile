package com.halokonsultan.mobile.ui.profile

import androidx.lifecycle.*
import com.halokonsultan.mobile.base.BaseViewModel
import com.halokonsultan.mobile.data.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
        private val repository: BaseRepository
) : BaseViewModel() {

    fun updateProfile(id: Int, name: String, province: String, city: String) = callApiReturnLiveData(
        apiCall = { repository.updateProfile(id, name, province, city) },
        handleBeforePostSuccess = {
            repository.setUserName(it.body()?.data?.name)
            repository.setUserCity(it.body()?.data?.city)
        }
    )

    fun getUserID(): Int {
        return repository.getUserId()
    }

    fun getAllProvince() = callApiReturnLiveData(
        apiCall = { repository.getAllProvince() }
    )

    fun getAllCities(id: String) = callApiReturnLiveData(
        apiCall = { repository.getAllCity(id) }
    )

    fun getProfileAdvance() = repository.getProfileAdvance(repository.getUserId()).asLiveData()

    fun logout() = callApiReturnLiveData(
        apiCall = { repository.logout() },
        handleBeforePostSuccess = { resetPref() }
    )

    fun resetPref() {
        repository.saveToken("")
        repository.setLoggedIn(false)
        repository.saveUserId(0)
        repository.setUserCity("")
        repository.setUserName("")
        repository.setExpirationTime(0)
    }
}