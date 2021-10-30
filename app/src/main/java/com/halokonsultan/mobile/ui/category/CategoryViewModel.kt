package com.halokonsultan.mobile.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.data.HaloKonsultanRepository
import com.halokonsultan.mobile.data.model.ParentCategory
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: HaloKonsultanRepository
) : ViewModel() {

    private val _categories: MutableLiveData<Resource<List<ParentCategory>>> = MutableLiveData()
    val categories: LiveData<Resource<List<ParentCategory>>>
        get() = _categories

    fun getAllCategories() = viewModelScope.launch {
        _categories.postValue(Resource.Loading())
        try {
            val response = repository.getAllCategories().body()
            if (response?.data != null) {
                _categories.postValue(Resource.Success(response.data))
            }
        } catch (e: Exception) {
            _categories.postValue(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }

}