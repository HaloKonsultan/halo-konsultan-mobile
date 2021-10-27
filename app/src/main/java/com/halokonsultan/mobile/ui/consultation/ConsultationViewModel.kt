package com.halokonsultan.mobile.ui.consultation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _consultationList: MutableLiveData<Resource<List<Consultation>>> = MutableLiveData()
    val consultationList: LiveData<Resource<List<Consultation>>>
        get() = _consultationList

    private val _consultation: MutableLiveData<Resource<DetailConsultation>> = MutableLiveData()
    val consultation: LiveData<Resource<DetailConsultation>>
        get() = _consultation

    private val _pay: MutableLiveData<Resource<DetailConsultation>> = MutableLiveData()
    val payResponse: LiveData<Resource<DetailConsultation>>
        get() = _pay

    fun getConsultationListBasedOnStatus(userId: Int, status: String)
    = viewModelScope.launch {
        _consultationList.postValue(Resource.Loading())
        try {
            val response = repository.getListConsultation(userId, status)
            _consultationList.postValue(Resource.Success(response.body()!!.data.data))
        } catch (e: Exception) {
            _consultationList.postValue(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }

    fun getDetailConsultation(id: Int) = viewModelScope.launch {
        _consultation.postValue(Resource.Loading())
        try {
            val response = repository.getDetailConsultation(id)
            _consultation.postValue(Resource.Success(response.body()!!.data))
        } catch (e: Exception) {
            _consultation.postValue(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }

    fun pay(id: Int) = viewModelScope.launch {
        _pay.postValue(Resource.Loading())
        try {
            val response = repository.pay(id)
            _pay.postValue(Resource.Success(response.body()!!.data))
        } catch (e: Exception) {
            _pay.postValue(Resource.Error(e.message ?: "unknown error"))
        }
    }

    fun getUserID() = repository.getUserId()
}