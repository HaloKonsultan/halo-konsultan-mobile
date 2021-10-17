package com.halokonsultan.mobile.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.halokonsultan.mobile.databinding.ActivityLandingBinding
import com.halokonsultan.mobile.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (viewModel.isLoggedIn()) {
            if (viewModel.isExpired()) {
                viewModel.resetPref()
                Toast.makeText(this, "Session telah expired, Silahkan login kembali",
                    Toast.LENGTH_LONG).show()
            } else {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}

