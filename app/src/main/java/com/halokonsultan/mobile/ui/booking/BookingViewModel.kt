package com.halokonsultan.mobile.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.data.HaloKonsultanRepository
import com.halokonsultan.mobile.data.model.DetailConsultation
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val repository: HaloKonsultanRepository
) : ViewModel() {

    private val _consultation: MutableLiveData<Resource<DetailConsultation>> = MutableLiveData()
    val consultation: LiveData<Resource<DetailConsultation>>
        get() = _consultation

    fun bookingConsultation(title: String, description: String, isOnline: Boolean, isOffline: Boolean, location: String)
    = viewModelScope.launch {
        _consultation.postValue(Resource.Loading())
        try {
            val response = repository.bookingConsultation(title, description, isOnline, isOffline, location)
            _consultation.postValue(Resource.Success(response.body()!!.data))
        } catch (e: Exception) {
            _consultation.postValue(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }
}