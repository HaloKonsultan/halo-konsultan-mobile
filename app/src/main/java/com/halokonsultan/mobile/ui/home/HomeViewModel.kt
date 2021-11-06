package com.halokonsultan.mobile.ui.home

import androidx.lifecycle.*
import com.halokonsultan.mobile.data.HaloKonsultanRepository
import com.halokonsultan.mobile.data.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HaloKonsultanRepository
) : ViewModel() {

    fun getRandomCategoriesAdvance() = repository.getRandomCategoriesAdvance().asLiveData()

    fun getNearestConsultantsAdvance(city: String) = repository.getNearestConsultantAdvance(city).asLiveData()

    fun categoriesWithOther(data: List<Category>?): List<Category> {
        val temp = data?.toMutableList() ?: mutableListOf()
        temp.add(Category(
            999,
            "https://res.cloudinary.com/anongtf/image/upload/v1634384878/i9actiizomb6fplq73xz.png",
            "Lainnya",
        ))
        return temp
    }
}