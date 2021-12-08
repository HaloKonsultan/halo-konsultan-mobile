package com.halokonsultan.mobile.ui.booking

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.afollestad.vvalidator.form
import com.halokonsultan.mobile.base.ActivityWithBackButton
import com.halokonsultan.mobile.data.model.DetailConsultation
import com.halokonsultan.mobile.databinding.ActivityBookingBinding
import com.halokonsultan.mobile.ui.confirmation.ConfirmationActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingActivity : ActivityWithBackButton<ActivityBookingBinding>() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_CATEGORY = "extra_category"
    }

    private val viewModel: BookingViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> ActivityBookingBinding
        = ActivityBookingBinding::inflate
    private val bookingObserver by lazy { setupObserver() }

    private var consultantId: Int? = null
    private var consultantName: String? = null
    private var consultantPhoto: String? = null
    private var consultantCategory: String? = null

    override fun setup() {
        populateDataFromBundle()
        bookingValidation()
        setChangeListener()
    }

    private fun setupObserver() = setObserverWithBasicResponse<DetailConsultation>(
            onSuccess = {
                binding.progressBar.visible()
                viewModel.sendNotification(
                    consultantId ?: 0,
                    "Pesanan Konsultasi Masuk",
                    "${viewModel.getUserName()}: $title"
                )
                intent = Intent(this, ConfirmationActivity::class.java)
                intent.putExtra(ConfirmationActivity.EXTRA_TITLE, "Booking berhasil!")
                intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Menunggu konfirmasi konsultan")
                startActivity(intent)
            },
            onError = { response ->
                binding.progressBar.gone()
                showToast(response.message.toString())
            },
            onLoading = {
                binding.progressBar.gone()
            }
        )


    private fun populateDataFromBundle() {
        val bundle = intent.extras
        if (bundle != null) {
            consultantId = intent.getIntExtra(EXTRA_ID, 0)
            consultantName = intent.getStringExtra(EXTRA_NAME)
            consultantPhoto = intent.getStringExtra(EXTRA_PHOTO)
            consultantCategory = intent.getStringExtra(EXTRA_CATEGORY)
        }

        with (binding) {
            tvConsultantName.text = consultantName
            tvConsultantCategory.text = consultantCategory
            Picasso.get().load(consultantPhoto).into(imgConsultant)
        }
    }

    private fun setChangeListener() {
        binding.cbOffline.setOnCheckedChangeListener { _, isChecked ->
            binding.tvLocationText.visibility = if (isChecked) View.VISIBLE else View.INVISIBLE
            binding.tfChooseLocation.visibility = if (isChecked) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun bookingValidation() {
        form {
            useRealTimeValidation(disableSubmit = true)
            input(binding.etTitle){
                isNotEmpty().description("Field judul harus diisi")
            }

            input(binding.etDescProblem){
                isNotEmpty().description("Field deskripsi harus diisi")
            }

            input(binding.etChooseLocation){
                conditional({binding.cbOffline.isChecked}){
                    isNotEmpty().description("Ketika memilih offline, Anda harus mengisi lokasi")
                }
            }

            submitWith(binding.btnBooking){
                try {
                    booking()
                } catch (e: Exception) {
                    showToast(e.message.toString())
                }
            }
        }
    }

    private fun booking() {
        val title = binding.etTitle.text
        val desc = binding.etDescProblem.text
        val isOnlineSelected = binding.cbOnline.isChecked
        val isOfflineSelected = binding.cbOffline.isChecked
        val location = if (isOfflineSelected) binding.etChooseLocation.text else "online"

        if (title.isNullOrBlank() || desc.isNullOrBlank() || (!isOnlineSelected && !isOfflineSelected)) {
            throw Exception("Anda harus mengisi field judul, deskripsi, " +
                    "dan memilih salah satu preferensi konsultasi")
        } else if (isOfflineSelected && location.isNullOrBlank()) {
            throw Exception("Ketika memilih offline, Anda harus mengisi lokasi")
        }

        viewModel.bookingConsultationV2(title.toString(),
                consultantId ?: 0,
                viewModel.getUserId(),
                desc.toString(),
                isOnlineSelected,
                isOfflineSelected,
                location.toString())
            .observe(this, bookingObserver)
    }
}