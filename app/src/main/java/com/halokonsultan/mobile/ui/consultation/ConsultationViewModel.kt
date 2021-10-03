package com.halokonsultan.mobile.ui.consultation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.data.HaloKonsultanRepository
import com.halokonsultan.mobile.data.model.Consultation
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConsultationViewModel @Inject constructor(
    private val repository: HaloKonsultanRepository
) : ViewModel() {

    private val _consultations: MutableLiveData<Resource<List<Consultation>>> = MutableLiveData()
    val consultation: LiveData<Resource<List<Consultation>>>
        get() = _consultations

    fun getConsultationListBasedOnStatus(userId: Int, status: String, limit: Int, page: Int)
    = viewModelScope.launch {
        _consultations.postValue(Resource.Loading())
        try {
            val response = repository.getListConsultation(userId, status, limit, page)
            _consultations.postValue(Resource.Success(response.body()!!.data))
        } catch (e: Exception) {
            _consultations.postValue(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }

}