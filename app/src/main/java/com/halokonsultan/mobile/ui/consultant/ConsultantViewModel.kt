package com.halokonsultan.mobile.ui.consultant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.model.Chat
import com.halokonsultan.mobile.data.model.DetailConsultant
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConsultantViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {

    private val _profile : MutableLiveData<Resource<DetailConsultant>> = MutableLiveData()
    val profile: LiveData<Resource<DetailConsultant>>
        get() = _profile

    private val _chat : MutableLiveData<Resource<Chat>> = MutableLiveData()
    val chat: LiveData<Resource<Chat>>
        get() = _chat

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

    fun openConversation(id:Int) = viewModelScope.launch {
        _chat.postValue(Resource.Loading())
        try {
            val response = repository.openConversation(repository.getUserId(), id)
            if (response.body()?.data != null) {
                _chat.postValue(Resource.Success(response.body()!!.data!!))
            }
        } catch (e: Exception) {
            _chat.postValue(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }
}