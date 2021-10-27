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
import com.halokonsultan.mobile.databinding.ActivityRegisterBinding
import com.halokonsultan.mobile.ui.main.MainActivity
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.utils.Utils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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

        val spannable = SpannableStringBuilder("Dengan menekan tombol Registrasi Akun, Anda telah menyetujui syarat dan ketentuan kami.")
        spannable.setSpan(UnderlineSpan(), 61, 86, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        binding.tvInformation.text = spannable

        binding.btnRegistrasi.setOnClickListener {
            if (validateRegister()) {
                register()
            } else {
                Toast.makeText(this, "Email harus valid dan password wajib diisi", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun register() {
        viewModel.register(
                binding.etNama.text.toString(),
                binding.etEmail.text.toString(),
                binding.etSandi.text.toString())
        viewModel.profile.observe(this, { data ->
            when(data) {
                is Resource.Success -> {
                    login(binding.etEmail.text.toString(), binding.etSandi.text.toString())
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

    private fun login(email: String, password: String) {
        viewModel.login(email, password)
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

    private fun validateRegister(): Boolean =
        !binding.etNama.text.isNullOrBlank()
                && !binding.etEmail.text.isNullOrBlank()
                && !binding.etSandi.text.isNullOrBlank()
                && binding.etEmail.text.toString().isValidEmail()

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