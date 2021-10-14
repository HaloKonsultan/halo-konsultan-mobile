package com.halokonsultan.mobile.ui.chooseconsultationtime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.data.HaloKonsultanRepository
import com.halokonsultan.mobile.data.model.DetailConsultation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseConsultationTimeViewModel @Inject constructor(
    private val repository: HaloKonsultanRepository): ViewModel() {

    private val _date: MutableLiveData<Resource<DetailConsultation>> = MutableLiveData()
    val date: LiveData<Resource<DetailConsultation>>
        get() = _date

    fun getPrefDate(id: Int, date: String) = viewModelScope.launch {
        _date.postValue(Resource.Loading())

        try {
            val response = repository.getPrefDate(id, date)
            _date.postValue(Resource.Success(response.body()!!.data))
        } catch (e: Exception) {
            _date.postValue(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}