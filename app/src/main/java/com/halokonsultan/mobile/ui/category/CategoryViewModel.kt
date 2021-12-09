package com.halokonsultan.mobile.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.base.BaseViewModel
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.model.ParentCategory
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: BaseRepository
) : BaseViewModel() {

    fun getAllCategories() = callApiReturnLiveData(
        apiCall = { repository.getAllCategories() }
    )
}