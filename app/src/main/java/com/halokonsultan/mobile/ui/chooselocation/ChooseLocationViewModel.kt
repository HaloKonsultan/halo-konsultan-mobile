package com.halokonsultan.mobile.ui.chooselocation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.base.BaseViewModel
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
) : BaseViewModel() {

    fun location(id: Int, name: String, province: String, city: String) = callApiReturnLiveData(
        apiCall = { repository.updateProfile(id, name, province, city) }
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
}