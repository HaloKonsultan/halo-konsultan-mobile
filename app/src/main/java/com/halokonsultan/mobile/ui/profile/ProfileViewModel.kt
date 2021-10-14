package com.halokonsultan.mobile.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.data.HaloKonsultanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
        private val repository: HaloKonsultanRepository
) : ViewModel() {

    fun logout() = viewModelScope.launch {
        repository.logout()
        repository.saveToken("")
        repository.setLoggedIn(false)
        repository.saveUserId(0)
    }

}