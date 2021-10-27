package com.halokonsultan.mobile.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.data.HaloKonsultanRepository
import com.halokonsultan.mobile.data.model.Profile
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
        private val repository: HaloKonsultanRepository
): ViewModel() {

    private val _profile : MutableLiveData<Resource<Profile>> = MutableLiveData()
    val profile: LiveData<Resource<Profile>>
        get() = _profile

    fun login(email: String, password: String) = viewModelScope.launch {
        _profile.postValue(Resource.Loading())
        try {
            val response = repository.login(email, password)
            _profile.postValue(Resource.Success(response.body()!!.data))
        } catch (e: Exception) {
            _profile.postValue(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}