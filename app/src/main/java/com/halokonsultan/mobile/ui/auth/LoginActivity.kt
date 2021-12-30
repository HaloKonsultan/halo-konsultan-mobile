package com.halokonsultan.mobile.ui.auth

import android.content.Intent
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import com.afollestad.vvalidator.form
import com.halokonsultan.mobile.base.ActivityWithBackButton
import com.halokonsultan.mobile.data.model.dto.AuthResponse
import com.halokonsultan.mobile.databinding.ActivityLoginBinding
import com.halokonsultan.mobile.ui.main.MainActivity
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ActivityWithBackButton<ActivityLoginBinding>() {

    private val viewModel: AuthViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> ActivityLoginBinding
        = ActivityLoginBinding::inflate

    private val loginObserver by lazy { setupObserver() }

    override fun setup() {
        setupSupportActionBar()
        validateIsLoggedIn()
        setupView()
        loginValidation()

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
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

    private fun setupView() {
        val spannable = SpannableStringBuilder("Belum punya akun? Registrasi")
        spannable.setSpan(UnderlineSpan(), 18, 28, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        binding.tvRegister.text = spannable
    }

    private fun validateIsLoggedIn() {
        if (viewModel.isLoggedIn()) {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loginValidation() {
        form {
            useRealTimeValidation()
            input(binding.etEmail, name = null){
                isNotEmpty().description("Field ini wajib diisi")
                isEmail().description("Silahkan masukan email yang valid!")
            }

            input(binding.etPassword, name = null){
                isNotEmpty().description("Field ini wajib diisi")
            }
            submitWith(binding.btnLogin){
                login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModel.getToken()
        viewModel.token.observe(this, { token ->
            if (token is Resource.Success) {
                viewModel.login(
                    email,
                    password,
                    token.data.toString()
                ).observe(this, loginObserver)
            }
        })
    }
}