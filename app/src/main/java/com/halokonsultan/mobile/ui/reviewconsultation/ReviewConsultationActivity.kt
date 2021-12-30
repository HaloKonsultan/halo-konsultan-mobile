package com.halokonsultan.mobile.ui.reviewconsultation

import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.halokonsultan.mobile.base.ActivityWithBackButton
import com.halokonsultan.mobile.data.model.Consultant
import com.halokonsultan.mobile.databinding.ActivityReviewConsultationBinding
import com.halokonsultan.mobile.ui.confirmation.ConfirmationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewConsultationActivity : ActivityWithBackButton<ActivityReviewConsultationBinding>() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override val bindingInflater: (LayoutInflater) -> ActivityReviewConsultationBinding
        = ActivityReviewConsultationBinding::inflate
    private val viewModel: ReviewViewModel by viewModels()
    private var id = 0

    override fun setup() {
        setupSupportActionBar()
        val bundle = intent.extras
        if (bundle != null) {
            id = intent.getIntExtra(EXTRA_ID, 0)
        }

        binding.btnLike.setOnClickListener {
            viewModel.review(id, true).observe(this, setupResponseObserver())
        }

        binding.btnDislike.setOnClickListener {
            viewModel.review(id, false).observe(this, setupResponseObserver())
        }
    }

    private fun setupResponseObserver() = setObserverWithBasicResponse<Consultant>(
        onSuccess = {
            binding.progressBar.gone()
            intent = Intent(this, ConfirmationActivity::class.java)
            intent.putExtra(ConfirmationActivity.EXTRA_TITLE, "Terima kasih Atas Reviewnya!")
            intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Review Anda sangat berguna bagi Konsultan untuk berkembang")
            startActivity(intent)
        },
        onError = { response ->
            binding.progressBar.gone()
            showToast(response.message.toString())
        },
        onLoading = { binding.progressBar.visible() }
    )
}