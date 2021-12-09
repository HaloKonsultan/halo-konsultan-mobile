package com.halokonsultan.mobile.ui.onboarding

import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.halokonsultan.mobile.base.BaseActivity
import com.halokonsultan.mobile.databinding.ActivityOnboardingBinding
import com.halokonsultan.mobile.ui.auth.LandingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(), OnBoardingView.OnBoardingChangeListener {

    override val bindingInflater: (LayoutInflater) -> ActivityOnboardingBinding
        = ActivityOnboardingBinding::inflate
    private val viewModel: OnBoardingViewModel by viewModels()

    override fun setup() {
        supportActionBar?.hide()
        binding.container.addListener(this)
    }

    override fun onFinish() {
        viewModel.finishOnBoarding()
        intent = Intent(this, LandingActivity::class.java)
        startActivity(intent)
        finish()
    }
}