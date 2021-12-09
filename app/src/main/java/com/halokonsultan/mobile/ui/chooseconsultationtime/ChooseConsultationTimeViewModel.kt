package com.halokonsultan.mobile.ui.chooseconsultationtime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.base.BaseViewModel
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.model.DetailConsultation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseConsultationTimeViewModel @Inject constructor(
    private val repository: BaseRepository): BaseViewModel() {

    private val _date: MutableLiveData<Resource<DetailConsultation>> = MutableLiveData()
    val date: LiveData<Resource<DetailConsultation>>
        get() = _date

    fun getPrefDate(id: Int, date: String, time: String) = callApiReturnLiveData(
        apiCall = { repository.getPrefDate(id, date, time) }
    )

//    fun getPrefDate(id: Int, date: String, time: String) = viewModelScope.launch {
//        _date.postValue(Resource.Loading())
//
//        try {
//            val response = repository.getPrefDate(id, date, time).body()
//            if (response?.data != null) {
//                _date.postValue(Resource.Success(response.data))
//            }
//        } catch (e: Exception) {
//            _date.postValue(Resource.Error(e.localizedMessage ?: "Unknown error"))
//        }
//    }
}