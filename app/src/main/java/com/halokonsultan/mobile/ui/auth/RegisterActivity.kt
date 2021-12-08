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
import com.halokonsultan.mobile.databinding.ActivityRegisterBinding
import com.halokonsultan.mobile.ui.chooselocation.ChooseLocationActivity
import com.halokonsultan.mobile.ui.main.MainActivity
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : ActivityWithBackButton<ActivityRegisterBinding>() {

    private val viewModel: AuthViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> ActivityRegisterBinding
        = ActivityRegisterBinding::inflate

    private val loginObserver by lazy { setupLoginObserver() }
    private val registerObserver by lazy { setupRegisterObserver() }

    override fun setup() {
        setupSupportActionBar()
        validateIsLoggedIn()
        setupView()
        registerValidation()
    }

    private fun setupRegisterObserver() = setObserver<AuthResponse>(
        onSuccess = { login(binding.etEmail.text.toString(), binding.etSandi.text.toString()) },
        onError = { data ->
            binding.progressBar.gone()
            Toast.makeText(this, data.message, Toast.LENGTH_LONG).show()
        },
        onLoading = { binding.progressBar.visible() }
    )

    private fun setupLoginObserver() = setObserver<AuthResponse>(
        onSuccess = { data ->
            binding.progressBar.gone()
            intent = Intent(this, ChooseLocationActivity::class.java)
            intent.putExtra(ChooseLocationActivity.EXTRA_ID, data.data?.data?.id)
            intent.putExtra(ChooseLocationActivity.EXTRA_NAME, data.data?.data?.name)
            startActivity(intent)
            finish()
        },
        onError = { data ->
            binding.progressBar.gone()
            Toast.makeText(this, data.message, Toast.LENGTH_LONG).show()
        },
        onLoading = { binding.progressBar.visible() }
    )

    private fun setupView() {
        val spannable = SpannableStringBuilder("Dengan menekan tombol Registrasi Akun, Anda telah menyetujui syarat dan ketentuan kami.")
        spannable.setSpan(UnderlineSpan(), 61, 86, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        binding.tvInformation.text = spannable
    }

    private fun validateIsLoggedIn() {
        if (viewModel.isLoggedIn()) {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerValidation() {
        form {
            useRealTimeValidation(disableSubmit = true)
            input(binding.etNama) {
                isNotEmpty().description("Field ini wajib diisi")
            }

            input(binding.etEmail) {
                isEmail().description("Silahkan masukan email yang valid!")
                isNotEmpty().description("Field ini wajib diisi")
            }

            submitWith(binding.btnRegistrasi) {
                register()
            }
        }
    }

    private fun register() {
        viewModel.register(
            binding.etNama.text.toString(),
            binding.etEmail.text.toString(),
            binding.etSandi.text.toString())
            .observe(this, registerObserver)
    }

    private fun login(email: String, password: String) {
        viewModel.getToken()
        viewModel.token.observe(this, { token ->
            if (token is Resource.Success) {
                viewModel.login(email, password, token.data.toString())
                    .observe(this, loginObserver)
            }
        })
    }
}