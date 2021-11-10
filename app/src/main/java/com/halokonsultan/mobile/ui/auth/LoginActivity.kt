package com.halokonsultan.mobile.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.UnderlineSpan
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.afollestad.vvalidator.form
import com.halokonsultan.mobile.databinding.ActivityLoginBinding
import com.halokonsultan.mobile.ui.main.MainActivity
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.utils.Utils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (viewModel.isLoggedIn()) {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val spannable = SpannableStringBuilder("Belum punya akun? Registrasi")
        spannable.setSpan(UnderlineSpan(), 18, 28, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        binding.tvRegister.text = spannable

        loginValidation()

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginValidation() {
        form{
            useRealTimeValidation()
            input(binding.etEmail, name = null){
                isNotEmpty().description("Field ini wajib diisi")
                isEmail().description("Silahkan masukan email yang valid!")
            }

            input(binding.etPassword, name = null){
                isNotEmpty().description("Field ini wajib diisi")
            }
            submitWith(binding.btnLogin){
                login()
            }
        }
    }

    private fun login() {
        viewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        viewModel.account.observe(this, { data ->
            when(data) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, data.message, Toast.LENGTH_LONG).show()
                }

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}