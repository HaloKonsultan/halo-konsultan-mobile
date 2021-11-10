package com.halokonsultan.mobile.ui.consultation

import androidx.lifecycle.*
import com.halokonsultan.mobile.data.HaloKonsultanRepository
import com.halokonsultan.mobile.data.model.Consultation
import com.halokonsultan.mobile.data.model.DetailConsultation
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConsultationViewModel @Inject constructor(
    private val repository: HaloKonsultanRepository
) : ViewModel() {

    private val _consultation: MutableLiveData<Resource<DetailConsultation>> = MutableLiveData()
    val consultation: LiveData<Resource<DetailConsultation>>
        get() = _consultation

    private val _pay: MutableLiveData<Resource<DetailConsultation>> = MutableLiveData()
    val payResponse: LiveData<Resource<DetailConsultation>>
        get() = _pay

    fun getConsultationByStatusAdvance(status: String, page: Int)
    = repository.getConsultationByStatusAdvance(getUserID(), status, page).asLiveData()

    fun getDetailConsultation(id: Int) = viewModelScope.launch {
        _consultation.postValue(Resource.Loading())
        try {
            val response = repository.getDetailConsultation(id).body()
            if (response?.data != null) {
                _consultation.postValue(Resource.Success(response.data))
            }
        } catch (e: Exception) {
            _consultation.postValue(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }

    fun pay(id: Int) = viewModelScope.launch {
        _pay.postValue(Resource.Loading())
        try {
            val response = repository.pay(id).body()
            if (response?.data != null) {
               _pay.postValue(Resource.Success(response.data))
            }
        } catch (e: Exception) {
            _pay.postValue(Resource.Error(e.message ?: "unknown error"))
        }
    }

    fun getUserID() = repository.getUserId()
}