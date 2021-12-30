package com.halokonsultan.mobile.ui.reviewconsultation


import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.base.BaseViewModel
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.utils.Utils.toInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
        private val repository: BaseRepository
) : BaseViewModel() {

    fun review(id: Int, isLike: Boolean) = callApiReturnLiveData(
        apiCall = { repository.reviewConsultation(id, isLike.toInt()) }
    )
}