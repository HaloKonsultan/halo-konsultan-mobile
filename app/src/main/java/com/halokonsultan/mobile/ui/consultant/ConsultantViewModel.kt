package com.halokonsultan.mobile.ui.consultant

import com.halokonsultan.mobile.base.BaseViewModel
import com.halokonsultan.mobile.data.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConsultantViewModel @Inject constructor(
    private val repository: BaseRepository
) : BaseViewModel() {

    fun getConsultantDetail(id: Int) = callApiReturnLiveData(
        apiCall = { repository.getConsultantDetail(id) }
    )

    fun openConversation(id:Int) = callApiReturnLiveData(
        apiCall = { repository.openConversation(repository.getUserId(), id) }
    )
}