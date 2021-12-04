package com.halokonsultan.mobile.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.halokonsultan.mobile.databinding.ActivityOnboardingBinding
import com.halokonsultan.mobile.ui.auth.LandingActivity
import com.halokonsultan.mobile.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity(), OnBoardingView.OnBoardingChangeListener {

    private lateinit var binding: ActivityOnboardingBinding
    private val viewModel: OnBoardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

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