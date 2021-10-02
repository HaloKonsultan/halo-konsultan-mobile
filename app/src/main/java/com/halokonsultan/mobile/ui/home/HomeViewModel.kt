package com.halokonsultan.mobile.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.data.HaloKonsultanRepository
import com.halokonsultan.mobile.data.model.Category
import com.halokonsultan.mobile.data.model.Consultant
import com.halokonsultan.mobile.data.model.Profile
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HaloKonsultanRepository
) : ViewModel() {

    private val _categories : MutableLiveData<Resource<List<Category>>> = MutableLiveData()
    val categories: LiveData<Resource<List<Category>>>
        get() = _categories

    private val _consultants : MutableLiveData<Resource<List<Consultant>>> = MutableLiveData()
    val consultants: LiveData<Resource<List<Consultant>>>
        get() = _consultants

    fun getRandomCategories() = viewModelScope.launch {
        _categories.postValue(Resource.Loading())
        try {
            val response = repository.getRandomCategories()
            var tempCategory = response.body()!!.data
            tempCategory = tempCategory.toMutableList()
            tempCategory.add(
                Category(
                    999,
                    "https://res.cloudinary.com/anongtf/image/upload/v1632140913/Perpajakan_sa3sbc.svg",
                    "Lainnya",
                    "others"
                )
            )
            _categories.postValue(Resource.Success(tempCategory))
        } catch (e: Exception) {
            _categories.postValue(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    fun getNearestConsultants(city: String) = viewModelScope.launch {
        _consultants.postValue(Resource.Loading())
        try {
            val response = repository.getNearestConsultants(city)
            _consultants.postValue(Resource.Success(response.body()!!.data))
        } catch (e: Exception) {
            _consultants.postValue(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

}