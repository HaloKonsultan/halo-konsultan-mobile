package com.halokonsultan.mobile.ui.consultation

import androidx.lifecycle.*
import com.halokonsultan.mobile.base.BaseViewModel
import com.halokonsultan.mobile.data.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConsultationViewModel @Inject constructor(
    private val repository: BaseRepository
) : BaseViewModel() {

    fun getConsultationByStatusAdvance(status: String, page: Int)
    = repository.getConsultationByStatusAdvance(repository.getUserId(), status, page).asLiveData()

    fun getDetailConsultation(id: Int) = callApiReturnLiveData(
        apiCall = { repository.getDetailConsultation(id) }
    )

    fun pay(id: Int, amount: Int) = callApiReturnLiveData(
        apiCall = { repository.pay(id, amount) }
    )
}