package com.halokonsultan.mobile.ui.chooselocation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.model.City
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


    fun getAllProvince() = viewModelScope.launch {
        _provinces.postValue(Resource.Loading())
        try {
            val response = repository.getAllProvince()
            if (response.body() != null) {
                _provinces.postValue(Resource.Success(response.body()!!.provinsi))
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
                _cities.postValue(Resource.Success(response.body()!!.kota_kabupaten))
            }
        } catch (e: Exception) {
            _cities.postValue(Resource.Error(e.localizedMessage?: "unknown error"))
        }
    }

}