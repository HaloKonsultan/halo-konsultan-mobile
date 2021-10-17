package com.halokonsultan.mobile.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.data.HaloKonsultanRepository
import com.halokonsultan.mobile.data.model.Consultant
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: HaloKonsultanRepository
) : ViewModel() {

    private val _consultants: MutableLiveData<Resource<List<Consultant>>> = MutableLiveData()
    val consultants: LiveData<Resource<List<Consultant>>>
        get() = _consultants

    fun searchConsultantByName(name: String) = viewModelScope.launch {
        _consultants.postValue(Resource.Loading())
        try {
            val response = repository.searchConsultantByName(name)
            _consultants.postValue(Resource.Success(response.body()!!.data.data))
        } catch (e: Exception) {
            _consultants.postValue(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }

    fun searchConsultantByCategory(id: Int) = viewModelScope.launch {
        _consultants.postValue(Resource.Loading())
        try {
            val response = repository.getConsultantByCategory(id)
            _consultants.postValue(Resource.Success(response.body()!!.data.data))
        } catch (e: Exception) {
            _consultants.postValue(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }
}