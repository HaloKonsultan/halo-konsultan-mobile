package com.halokonsultan.mobile.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.halokonsultan.mobile.databinding.ActivityLoginBinding
import com.halokonsultan.mobile.ui.main.MainActivity
import com.halokonsultan.mobile.ui.register.RegisterActivity
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.utils.Utils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Login"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.btnLogin.setOnClickListener {
            if (validateLogin()) {
//                login()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Email harus valid dan password wajib diisi", Toast.LENGTH_LONG).show()
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login() {
        viewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        viewModel.profile.observe(this, { data ->
            when(data) {
                is Resource.Success -> {
                    // save profile to sharedPref
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

    private fun validateLogin(): Boolean =
            !binding.etEmail.text.isNullOrBlank()
                    && !binding.etPassword.text.isNullOrBlank()
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