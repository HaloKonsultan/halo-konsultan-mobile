package com.halokonsultan.mobile.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.data.HaloKonsultanRepository
import com.halokonsultan.mobile.data.model.dto.LogoutResponse
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
        private val repository: HaloKonsultanRepository
) : ViewModel() {

    private val _logout: MutableLiveData<Resource<LogoutResponse>> = MutableLiveData()
    val logout: LiveData<Resource<LogoutResponse>>
        get() = _logout

    fun logout() = viewModelScope.launch {
        _logout.postValue(Resource.Loading())
        try {
            val response = repository.logout()
            repository.saveToken("")
            repository.setLoggedIn(false)
            repository.saveUserId(0)
            _logout.postValue(Resource.Success(response.body()!!))
        } catch (e: Exception) {
            _logout.postValue(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }

}