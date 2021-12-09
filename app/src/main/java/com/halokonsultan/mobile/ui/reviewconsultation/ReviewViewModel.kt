package com.halokonsultan.mobile.ui.reviewconsultation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.base.BaseViewModel
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
) : BaseViewModel() {

    fun review(id: Int, isLike: Boolean) = callApiReturnLiveData(
        apiCall = { repository.reviewConsultation(id, isLike.toInt()) }
    )
}