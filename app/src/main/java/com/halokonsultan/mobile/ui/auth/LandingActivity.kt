package com.halokonsultan.mobile.ui.auth

import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import com.halokonsultan.mobile.base.BaseActivity
import com.halokonsultan.mobile.data.model.dto.AuthResponse
import com.halokonsultan.mobile.databinding.ActivityLandingBinding
import com.halokonsultan.mobile.ui.main.MainActivity
import com.halokonsultan.mobile.ui.onboarding.OnboardingActivity
import com.halokonsultan.mobile.utils.Resource
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

        binding.btnLoginDemo.setOnClickListener {
            login(DEMO_EMAIL, DEMO_PASSWORD)
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

    private fun setupObserver() = setObserver<AuthResponse>(
        onSuccess = {
            binding.progressBar.gone()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        },
        onError = { data ->
            binding.progressBar.gone()
            Toast.makeText(this, data.message, Toast.LENGTH_LONG).show()
        },
        onLoading = { binding.progressBar.visible() }
    )

    private fun login(email: String, password: String) {
        viewModel.getToken()
        viewModel.token.observe(this, { token ->
            if (token is Resource.Success) {
                viewModel.login(
                    email,
                    password,
                    token.data.toString()
                ).observe(this, setupObserver())
            }
        })
    }

    companion object {
        const val DEMO_EMAIL = "demo.client@halokonsultan.me"
        const val DEMO_PASSWORD = "password"
    }
}

