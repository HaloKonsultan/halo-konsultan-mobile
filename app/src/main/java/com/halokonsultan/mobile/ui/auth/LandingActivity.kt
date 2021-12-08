package com.halokonsultan.mobile.ui.auth

import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.halokonsultan.mobile.base.BaseActivity
import com.halokonsultan.mobile.databinding.ActivityLandingBinding
import com.halokonsultan.mobile.ui.main.MainActivity
import com.halokonsultan.mobile.ui.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingActivity : BaseActivity<ActivityLandingBinding>() {

    private val viewModel: AuthViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> ActivityLandingBinding
        = ActivityLandingBinding::inflate

    override fun setup() {
        validateRedirection()
        setupButtonListener()
    }

    private fun setupButtonListener() {
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validateRedirection() {
        if (viewModel.isFirstTime()) {
            startActivity(Intent(this, OnboardingActivity::class.java))
        }

        if (viewModel.isLoggedIn()) {
            if (viewModel.isExpired()) {
                viewModel.resetPref()
                showToast("Session telah expired, Silahkan login kembali")
            } else {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}

