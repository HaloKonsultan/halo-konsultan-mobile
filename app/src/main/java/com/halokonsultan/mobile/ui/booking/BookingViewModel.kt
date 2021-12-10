package com.halokonsultan.mobile.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.base.BaseViewModel
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.model.DetailConsultation
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.utils.Utils.toInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val repository: BaseRepository
) : BaseViewModel() {

    fun bookingConsultationV2(title: String, consultantId: Int, userId: Int, description: String,
                              isOnline: Boolean, isOffline: Boolean, location: String)
        = callApiReturnLiveData(
            apiCall = {
                repository.bookingConsultation(
                    title,
                    consultantId,
                    userId,
                    description,
                    isOnline.toInt(),
                    isOffline.toInt(),
                    location
                )
            }
        )

    fun getUserId() = repository.getUserId()

    fun sendNotification(id: Int, title: String, message: String) = viewModelScope.launch {
        repository.sendNotification(id, title, message)
    }

    fun getUserName() = repository.getUserName()
}