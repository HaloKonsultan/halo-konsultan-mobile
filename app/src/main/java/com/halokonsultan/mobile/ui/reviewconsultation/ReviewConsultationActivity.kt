package com.halokonsultan.mobile.ui.reviewconsultation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.halokonsultan.mobile.databinding.ActivityReviewConsultationBinding
import com.halokonsultan.mobile.ui.confirmation.ConfirmationActivity
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewConsultationActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityReviewConsultationBinding
    private val viewModel: ReviewViewModel by viewModels()
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewConsultationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        if (bundle != null) {
            id = intent.getIntExtra(EXTRA_ID, 0)
        }

        binding.btnLike.setOnClickListener {
            viewModel.review(id, true)
        }

        binding.btnDislike.setOnClickListener {
            viewModel.review(id, false)
        }

        observeResponse()
    }

    private fun observeResponse() {
        viewModel.consultant.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    intent = Intent(this, ConfirmationActivity::class.java)
                    intent.putExtra(ConfirmationActivity.EXTRA_TITLE, "Terima kasih Atas Reviewnya!")
                    intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Review Anda sangat berguna bagi Konsultan untuk berkembang")
                    startActivity(intent)
                }
                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }
}