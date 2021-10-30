package com.halokonsultan.mobile.ui.consultant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.data.HaloKonsultanRepository
import com.halokonsultan.mobile.data.model.DetailConsultant
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConsultantViewModel @Inject constructor(
    private val repository: HaloKonsultanRepository
) : ViewModel() {

    private val _profile : MutableLiveData<Resource<DetailConsultant>> = MutableLiveData()
    val profile: LiveData<Resource<DetailConsultant>>
        get() = _profile

    fun getConsultantDetail(id: Int) = viewModelScope.launch {
        _profile.postValue(Resource.Loading())
        try {
            val response = repository.getConsultantDetail(id).body()
            if (response?.data != null) {
                _profile.postValue(Resource.Success(response.data))
            }
        } catch (e: Exception) {
            _profile.postValue(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }
}