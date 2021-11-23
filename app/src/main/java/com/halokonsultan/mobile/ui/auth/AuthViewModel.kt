package com.halokonsultan.mobile.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.model.Profile
import com.halokonsultan.mobile.data.model.dto.AuthResponse
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
        private val repository: BaseRepository
) : ViewModel() {

    private val _token: MutableLiveData<Resource<String>> = MutableLiveData()
    val token: LiveData<Resource<String>>
        get() = _token

    private val _profile : MutableLiveData<Resource<Profile>> = MutableLiveData()
    val profile: LiveData<Resource<Profile>>
        get() = _profile

    private val _account : MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val account: LiveData<Resource<AuthResponse>>
        get() = _account

    fun register(name: String, email: String, password: String) = viewModelScope.launch {
        _profile.postValue(Resource.Loading())
        try {
            val response = repository.register(name, email, password)
            _profile.postValue(Resource.Success(response.body()!!.data))
        } catch (e: Exception) {
            _profile.postValue(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    fun login(email: String, password: String, token: String) = viewModelScope.launch {
        _account.postValue(Resource.Loading())
        try {
            val response = repository.login(email, password, token)
            repository.saveToken(response.body()!!.access_token)
            repository.setLoggedIn(true)
            repository.saveUserId(response.body()!!.data.id)
            repository.setUserName(response.body()!!.data.name)
            repository.setUserCity(response.body()!!.data.city)
            repository.setExpirationTime(response.body()!!.expires_in)
            _account.postValue(Resource.Success(response.body()!!))
        } catch (e: Exception) {
            _account.postValue(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

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
                Log.w("coba", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            _token.postValue(Resource.Success(task.result.toString()))
        })
    }
}