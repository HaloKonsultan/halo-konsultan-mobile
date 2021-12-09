package com.halokonsultan.mobile.ui.profile

import androidx.lifecycle.*
import com.halokonsultan.mobile.base.BaseViewModel
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.model.dto.LogoutResponse
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
        private val repository: BaseRepository
) : BaseViewModel() {

    fun getProfileAdvance() = repository.getProfileAdvance(repository.getUserId()).asLiveData()

    fun logout() = callApiReturnLiveData(
        apiCall = { repository.logout() },
        handleBeforePostSuccess = { resetPref() }
    )

    fun resetPref() {
        repository.saveToken("")
        repository.setLoggedIn(false)
        repository.saveUserId(0)
        repository.setUserCity("")
        repository.setUserName("")
        repository.setExpirationTime(0)
    }
}