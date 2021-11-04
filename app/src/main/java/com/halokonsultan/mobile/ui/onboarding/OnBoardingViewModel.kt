package com.halokonsultan.mobile.ui.onboarding

import androidx.lifecycle.ViewModel
import com.halokonsultan.mobile.data.HaloKonsultanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val repository: HaloKonsultanRepository
) : ViewModel() {
    fun finishOnBoarding() = repository.setFirstTime(false)
}