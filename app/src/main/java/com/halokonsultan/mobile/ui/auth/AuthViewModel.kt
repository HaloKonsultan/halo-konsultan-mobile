package com.halokonsultan.mobile.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.halokonsultan.mobile.base.BaseViewModel
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
        private val repository: BaseRepository
) : BaseViewModel() {

    private val _token: MutableLiveData<Resource<String>> = MutableLiveData()
    val token: LiveData<Resource<String>>
        get() = _token

    fun register(name: String, email: String, password: String) = callApiReturnLiveData(
        apiCall = { repository.register(name, email, password) }
    )

    fun login(email: String, password: String, token: String) = callApiReturnLiveData(
        apiCall = { repository.login(email, password, token) },
        handleBeforePostSuccess = { response ->
            repository.saveToken(response.body()!!.access_token)
            repository.setLoggedIn(true)
            repository.saveUserId(response.body()!!.data.id)
            repository.setUserName(response.body()!!.data.name)
            repository.setUserCity(response.body()!!.data.city)
            repository.setExpirationTime(response.body()!!.expires_in)
        }
    )

    fun resetPref() {
        repository.saveToken("")
        repository.setLoggedIn(false)
        repository.saveUserId(0)
        repository.setExpirationTime(0)
    }

    fun isLoggedIn() = repository.isLoggedIn()
    fun isExpired(): Boolean {
        val cal = Calendar.getInstance()
        return cal.timeInMillis >= repository.getExpiredTime()
    }

    fun isFirstTime() = repository.isFirstTime()

    fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            _token.postValue(Resource.Success(task.result.toString()))
        })
    }
}