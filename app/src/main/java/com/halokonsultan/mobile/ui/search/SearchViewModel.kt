package com.halokonsultan.mobile.ui.search

import androidx.lifecycle.*
import com.halokonsultan.mobile.base.BaseViewModel
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.utils.GlobalState
import com.halokonsultan.mobile.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: BaseRepository
) : BaseViewModel() {

    fun searchConsultantByName(name: String, page: Int) = callApiReturnLiveData(
        apiCall = { repository.searchConsultantByName(name, page) },
        handleBeforePostSuccess = { response ->
            GlobalState.nextPageConsultant =
                if (response.body()?.data?.next_page_url != null)
                    Utils.getPageNumberFromUrl(response.body()?.data?.next_page_url!!)
                else
                    null
        }
    )

    fun searchConsultantByCategoryAdvance(id: Int, page: Int) = repository.getConsultantByCategoryAdvance(id, page).asLiveData()
}