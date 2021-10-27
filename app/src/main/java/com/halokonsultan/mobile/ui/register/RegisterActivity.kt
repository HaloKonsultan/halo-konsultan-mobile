package com.halokonsultan.mobile.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.ActivityRegisterBinding
import com.halokonsultan.mobile.ui.main.MainActivity
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.utils.Utils.isValidEmail

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Register"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.btnRegistrasi.setOnClickListener {
            if (validateRegister()) {
//                register()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
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