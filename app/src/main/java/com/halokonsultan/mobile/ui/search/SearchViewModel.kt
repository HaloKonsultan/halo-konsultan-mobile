package com.halokonsultan.mobile.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.halokonsultan.mobile.data.HaloKonsultanRepository
import com.halokonsultan.mobile.data.model.Consultant
import com.halokonsultan.mobile.utils.GlobalState
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: HaloKonsultanRepository
) : ViewModel() {

    private val _consultants: MutableLiveData<Resource<List<Consultant>>> = MutableLiveData()
    val consultants: LiveData<Resource<List<Consultant>>>
        get() = _consultants

    fun searchConsultantByName(name: String, page: Int) = viewModelScope.launch {
        _consultants.postValue(Resource.Loading())
        try {
            Log.d("coba", "searchConsultantByName: $name $page")
            val response = repository.searchConsultantByName(name, page)
            GlobalState.nextPageConsultant =
                    if (response.body()?.data?.next_page_url != null)
                        Utils.getPageNumberFromUrl(response.body()?.data?.next_page_url!!)
                    else
                        null
            _consultants.postValue(Resource.Success(response.body()!!.data.data))
        } catch (e: Exception) {
            _consultants.postValue(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }

    fun searchConsultantByCategoryAdvance(id: Int, page: Int) = repository.getConsultantByCategoryAdvance(id, page).asLiveData()
}