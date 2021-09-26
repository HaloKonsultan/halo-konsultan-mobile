package com.halokonsultan.mobile.ui.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.ActivityLandingBinding
import com.halokonsultan.mobile.ui.login.LoginActivity
import com.halokonsultan.mobile.ui.main.MainActivity
import com.halokonsultan.mobile.ui.register.RegisterActivity

class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }
}

