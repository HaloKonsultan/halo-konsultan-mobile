package com.halokonsultan.mobile.ui.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.halokonsultan.mobile.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }
}