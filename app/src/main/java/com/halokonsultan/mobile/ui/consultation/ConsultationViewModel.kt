package com.halokonsultan.mobile.ui.consultation

import androidx.lifecycle.*
import com.halokonsultan.mobile.base.BaseViewModel
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.model.Consultation
import com.halokonsultan.mobile.data.model.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    fun isReviewExist(consultationId:Int) = repository.isReviewExists(repository.getUserId(), consultationId)

    fun upsertReview(consultation: Consultation) = viewModelScope.launch {
            repository.upsertReview(Review(0, consultation.id, consultation.user_id, false))
        }

    fun getReview(consultationId: Int) = repository.getReview(consultationId)
}