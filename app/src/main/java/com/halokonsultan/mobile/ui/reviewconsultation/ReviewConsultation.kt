package com.halokonsultan.mobile.ui.reviewconsultation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.halokonsultan.mobile.databinding.ActivityReviewConsultationBinding

class ReviewConsultation : AppCompatActivity() {
    private lateinit var binding: ActivityReviewConsultationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewConsultationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}