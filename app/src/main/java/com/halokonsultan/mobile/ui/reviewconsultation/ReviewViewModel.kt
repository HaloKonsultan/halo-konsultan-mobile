package com.halokonsultan.mobile.ui.reviewconsultation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.model.Consultant
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.utils.Utils
import com.halokonsultan.mobile.utils.Utils.toInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
        private val repository: BaseRepository
) : ViewModel() {

    private val _consultant: MutableLiveData<Resource<Consultant>> = MutableLiveData()
    val consultant: LiveData<Resource<Consultant>>
        get() = _consultant

    fun review(id: Int, isLike: Boolean) = viewModelScope.launch {
        _consultant.postValue(Resource.Loading())
        try {
            val response = repository.reviewConsultation(id, isLike.toInt())
            if (response.body() != null && response.body()?.data != null) {
                _consultant.postValue(Resource.Success(response.body()?.data!!))
            }
        } catch (e: Exception) {
            _consultant.postValue(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }
}