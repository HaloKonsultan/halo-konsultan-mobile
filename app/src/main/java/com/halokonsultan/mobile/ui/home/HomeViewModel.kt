package com.halokonsultan.mobile.ui.home

import androidx.lifecycle.*
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.model.Category
import com.halokonsultan.mobile.data.model.Consultation
import com.halokonsultan.mobile.data.model.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {

    fun getUserCity() = repository.getUserCity()

    fun getRandomCategoriesAdvance() = repository.getRandomCategoriesAdvance().asLiveData()

    fun getNearestConsultantsAdvance(city: String, page: Int) = repository.getNearestConsultantAdvance(city, page).asLiveData()

    fun categoriesWithOther(data: List<Category>?): List<Category> {
        val temp = data?.toMutableList() ?: mutableListOf()
        temp.add(Category(
            999,
            "https://res.cloudinary.com/anongtf/image/upload/v1634384878/i9actiizomb6fplq73xz.png",
            "Lainnya",
        ))
        return temp
    }

    fun getLatestConsultation() = repository.getLatestConsultation(repository.getUserId()).asLiveData()
}