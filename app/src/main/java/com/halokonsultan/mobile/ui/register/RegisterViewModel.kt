package com.halokonsultan.mobile.ui.register

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
class RegisterViewModel @Inject constructor(
        private val repository: HaloKonsultanRepository
) : ViewModel() {

    private val _profile : MutableLiveData<Resource<Profile>> = MutableLiveData()
    val profile: LiveData<Resource<Profile>>
        get() = _profile

    fun register(name: String, email: String, password: String) = viewModelScope.launch {
        _profile.postValue(Resource.Loading())
        try {
            val response = repository.register(name, email, password)
            _profile.postValue(Resource.Success(response.body()!!.data))
        } catch (e: Exception) {
            _profile.postValue(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

}