package com.halokonsultan.mobile.ui.profile

import androidx.lifecycle.*
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.model.dto.LogoutResponse
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
        private val repository: BaseRepository
) : ViewModel() {

    private val _logout: MutableLiveData<Resource<LogoutResponse>> = MutableLiveData()
    val logout: LiveData<Resource<LogoutResponse>>
        get() = _logout

    fun getProfileAdvance() = repository.getProfileAdvance(repository.getUserId()).asLiveData()

    fun logout() = viewModelScope.launch {
        _logout.postValue(Resource.Loading())
        try {
            val response = repository.logout()
            resetPref()
            _logout.postValue(Resource.Success(response.body()!!))
        } catch (e: Exception) {
            _logout.postValue(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }

    fun resetPref() {
        repository.saveToken("")
        repository.setLoggedIn(false)
        repository.saveUserId(0)
        repository.setUserCity("")
        repository.setUserName("")
        repository.setExpirationTime(0)
    }

}