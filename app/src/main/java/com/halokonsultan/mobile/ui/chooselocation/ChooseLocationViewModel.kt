package com.halokonsultan.mobile.ui.chooselocation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.model.City
import com.halokonsultan.mobile.data.model.Profile
import com.halokonsultan.mobile.data.model.Province
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseLocationViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {

    private val _provinces: MutableLiveData<Resource<List<Province>>> = MutableLiveData()
    val provinces: LiveData<Resource<List<Province>>>
        get() = _provinces

    private val _cities: MutableLiveData<Resource<List<City>>> = MutableLiveData()
    val cities: LiveData<Resource<List<City>>>
        get() = _cities

    private val _profile : MutableLiveData<Resource<Profile>> = MutableLiveData()
    val profile: LiveData<Resource<Profile>>
        get() = _profile

    fun location(id: Int,
                 name: String,
                 province: String,
                 city: String) = viewModelScope.launch {
        _profile.postValue(Resource.Loading())
        try {
            val response = repository.updateProfile(id, name, province, city)
            if (response.body() != null) {
                _profile.postValue(Resource.Success(response.body()!!.data!!))
            }
        } catch (e: Exception) {
            _profile.postValue(Resource.Error(e.localizedMessage?: "unknown error"))
        }
    }

    fun getUserID(): Int {
        return repository.getUserId()
    }


    fun getAllProvince() = viewModelScope.launch {
        _provinces.postValue(Resource.Loading())
        try {
            val response = repository.getAllProvince()
            if (response.body() != null) {
                _provinces.postValue(Resource.Success(response.body()!!.rajaongkir.results))
            }
        } catch (e: Exception) {
            _provinces.postValue(Resource.Error(e.localizedMessage?: "unknown error"))
        }
    }

    fun getAllCities(id: Int) = viewModelScope.launch {
        _cities.postValue(Resource.Loading())
        try {
            val response = repository.getAllCity(id)
            if (response.body() != null) {
                _cities.postValue(Resource.Success(response.body()!!.rajaongkir.results))
            }
        } catch (e: Exception) {
            _cities.postValue(Resource.Error(e.localizedMessage?: "unknown error"))
        }
    }

}